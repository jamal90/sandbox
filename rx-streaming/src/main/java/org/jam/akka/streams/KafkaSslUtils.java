package org.jam.akka.streams;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class KafkaSslUtils {

	private static final Logger log = LoggerFactory.getLogger(KafkaSslUtils.class);

	private KafkaSslUtils() { }

	public static Map<String, String> getSslProperties(Map<String, String> kafkaConfig) {
		Map<String, String> sslProps = new ConcurrentHashMap<>();

		if (kafkaConfig.get("ca_cert") == null || kafkaConfig.get("username") == null
				|| kafkaConfig.get("password") == null || kafkaConfig.get("token_key") == null) {
			log.debug("skipping sasl - no sasl information provided");
			return sslProps;
		}

		try {
			String trustStorePass = kafkaConfig.get("password"); // re-use as truststore password
			sslProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
			sslProps.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
			sslProps.put(SaslConfigs.SASL_JAAS_CONFIG,
					getJaasString(kafkaConfig.get("token_key"), kafkaConfig.get("username"),
							kafkaConfig.get("password")));
			sslProps.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,
					createTruststoreWithRootCert(trustStorePass, kafkaConfig.get("ca_cert")));
			sslProps.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
			sslProps.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, trustStorePass);
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException ex) {
			log.error("Error reading SSL Properties", ex);
		}
		return sslProps;
	}

	private static String getJaasString(String tokenUrl, String username, String password) throws IOException {
		String base =
				"org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password="
						+ "\"%s\";";
		String token = getToken(tokenUrl, username, password);
		return String.format(base, username, token);
	}

	private static String getToken(String tokenUrl, String user, String password) throws IOException {

		String userCredentials = user + ":" + password;
		String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString(userCredentials.getBytes());
		String bodyParams = "grant_type=client_credentials";
		byte[] postData = bodyParams.getBytes(StandardCharsets.UTF_8);
		URL url = new URL(tokenUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Authorization", authHeaderValue);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length", "" + postData.length);
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		try (DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
			os.write(postData);
		}
		String resp;
		try (DataInputStream is = new DataInputStream(conn.getInputStream());
			 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			resp = br.lines().collect(Collectors.joining("\n"));
		}
		conn.disconnect();

		return new Gson().fromJson(resp, JsonObject.class).get("access_token").getAsString();
	}

	private static String createTruststoreWithRootCert(String password, String certUrl)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

		// Load Certificate from URL
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		Certificate certificate = cf.generateCertificate(new URL(certUrl).openStream());

		// Create the keystore
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(null, password.toCharArray()); // null creates an empty store

		// Add the certificate
		keystore.setCertificateEntry("KafkaRootCA", certificate);

		// Save the new keystore
		File keystoreFile = File.createTempFile("kafkaTrustStore", null);
		try (FileOutputStream os = new FileOutputStream(keystoreFile)) {
			keystore.store(os, password.toCharArray());
		}
		return keystoreFile.getAbsolutePath();
	}

	public static void main(String[] args) {
		Map<String, String> kafkaConfig = new HashMap<>();
		kafkaConfig.put("ca_cert", "https://kafka-service-broker.cf.sap.hana.ondemand.com/certs/rootCA.crt");
		kafkaConfig.put("username", "sbss_h_jxalu20t423tr-fq68gzr14l5jvo36ih2rendq9zmowa5rjri9mhjk1wlwqfmdfqe=");
		kafkaConfig.put("password", "aa_CkAL1ajhuG1DtkiOadh5shmQtB8=");
		kafkaConfig.put("token_key", "https://kafka-service-oauth.cf.sap.hana.ondemand.com/v1/efa5bc12-837c-4bb6-bbf9-3962201a0816/token");

		System.out.println(getSslProperties(kafkaConfig));
	}
}