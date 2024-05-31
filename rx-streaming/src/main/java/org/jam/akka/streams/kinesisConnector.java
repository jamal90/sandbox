package org.jam.akka.streams;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.kafka.ProducerSettings;
import akka.kafka.javadsl.Producer;
import akka.stream.ActorMaterializer;
import akka.stream.alpakka.kinesis.ShardSettings;
import akka.stream.alpakka.kinesis.javadsl.KinesisSource;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.AmazonKinesisAsync;
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClientBuilder;
import com.amazonaws.services.kinesis.model.Record;
import com.amazonaws.services.kinesis.model.ShardIteratorType;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class kinesisConnector {

	public static void main(String[] args) {
		final ActorSystem actorSystem = ActorSystem.create("kcl-connector");
		final ActorMaterializer actorMaterializer = ActorMaterializer.create(actorSystem);

		String accessKey = System.getEnv("access_key");
        String accessSecret = System.getEnv("access_secret");
		BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, accessSecret);
		AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(cred);

		final AmazonKinesisAsync kinesisClient = AmazonKinesisAsyncClientBuilder.standard()
				.withCredentials(credentialsProvider)
				.withRegion("eu-central-1")
				.build();

		final ShardSettings shardSettings = ShardSettings.create("dh-kinesis-1", "shardId-000000000000")
				.withRefreshInterval(Duration.ofSeconds(1))
				.withLimit(500)
				.withShardIteratorType(ShardIteratorType.LATEST);

		final Source<Record, NotUsed> kinesisSource = KinesisSource.basic(shardSettings, kinesisClient);

		final Config config1 = actorSystem.settings().config().getConfig("akka.kafka.producer");

//		String kafkaBrokers = "localhost:9092";

		String kafkaBrokers = "10.254.20.21:9093,10.254.20.22:9093,10.254.20.23:9093";
		final String configStr = "{\"close-timeout\":\"60s\",\"eos-commit-interval\":\"100ms\",\"kafka-clients\":{\"bootstrap\":{\"servers\":\"" + kafkaBrokers + "\"}},\"parallelism\":100,\"use-dispatcher\":\"akka.kafka.default-dispatcher\"}";

		final Config config = ConfigFactory.parseString(configStr);

		ProducerSettings<String, String> producerSettings = ProducerSettings.create(config, new StringSerializer(), new StringSerializer());

		Map<String, String> kafkaConfig = new HashMap<>();
		kafkaConfig.put("ca_cert", "https://kafka-service-broker.cf.sap.hana.ondemand.com/certs/rootCA.crt");
		kafkaConfig.put("username", "sbss_h_jxalu20t423tr-fq68gzr14l5jvo36ih2rendq9zmowa5rjri9mhjk1wlwqfmdfqe=");
		kafkaConfig.put("password", "aa_CkAL1ajhuG1DtkiOadh5shmQtB8=");
		kafkaConfig.put("token_key", "https://kafka-service-oauth.cf.sap.hana.ondemand.com/v1/efa5bc12-837c-4bb6-bbf9-3962201a0816/token");
		producerSettings = producerSettings.withProperties(KafkaSslUtils.getSslProperties(kafkaConfig));


		final Sink<ProducerRecord<String, String>, CompletionStage<Done>> sink = Producer.plainSink(producerSettings);

		final RunnableGraph<NotUsed> graph = kinesisSource
				.map(record -> {
					return new String(record.getData().array(), StandardCharsets.UTF_8);
				})
				.map(deviceShadowMsg -> {
					return new ProducerRecord<>("c2c-interop-kinesis-sink", "key1", deviceShadowMsg);
				})
				.alsoTo(Sink.foreach(x -> System.out.println("__MESSAGE__" + x)))
				.to(sink);

		actorSystem.registerOnTermination(kinesisClient::shutdown);

		graph.run(actorMaterializer);

	}
}