package streams;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BasicStreamTest {

	public static void main(String[] args) throws IOException {

		int count = 10000;
		AtomicInteger curr = new AtomicInteger(0);

		final CloseableHttpClient httpClient = HttpClients.createDefault();

		final HttpPost httpPost = new HttpPost("https://adxtimeseriesstore.westus2.kusto.windows.net/v2/rest/query");

		final StringEntity stringEntity = new StringEntity("{" +
				"  \"db\":\"timeseries-db-2\"," +
				"  \"csl\":\"set notruncation; IG2 | take 100000\"," +
				"  \"properties\":\"{\\\"Options\\\":{\\\"queryconsistency\\\":\\\"strongconsistency\\\", \\\"results_progressive_enabled\\\": \\\"true\\\", \\\"query_results_progressive_row_count\\\": 10000},\\\"Parameters\\\":{},\\\"ClientRequestId\\\":\\\"MyApp.Query;e9f884e4-90f0-404a-8e8b-01d883023bf1\\\"}\"" +
				"}", StandardCharsets.UTF_8);
		httpPost.setEntity(stringEntity);
		httpPost.setHeader("Authorization", "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IkhsQzBSMTJza3hOWjFXUXdtak9GXzZ0X3RERSIsImtpZCI6IkhsQzBSMTJza3hOWjFXUXdtak9GXzZ0X3RERSJ9.eyJhdWQiOiJodHRwczovL2FkeHRpbWVzZXJpZXNzdG9yZS53ZXN0dXMyLmt1c3RvLndpbmRvd3MubmV0IiwiaXNzIjoiaHR0cHM6Ly9zdHMud2luZG93cy5uZXQvNDJmNzY3NmMtZjQ1NS00MjNjLTgyZjYtZGMyZDk5NzkxYWY3LyIsImlhdCI6MTU4MDk5Mzk0NywibmJmIjoxNTgwOTkzOTQ3LCJleHAiOjE1ODA5OTc4NDcsImFpbyI6IjQyTmdZRWpvazcvZXhPVnF2TWQ3UytMbnE0V1RBUT09IiwiYXBwaWQiOiJjZTNmNDM0NS0wMTQ1LTQ3YjQtYTAxMS1jZWJmMzE0YTJiODQiLCJhcHBpZGFjciI6IjEiLCJpZHAiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80MmY3Njc2Yy1mNDU1LTQyM2MtODJmNi1kYzJkOTk3OTFhZjcvIiwib2lkIjoiOWQxNDNkZWMtNjY0MC00ODM3LWFiNzAtN2NkNDExYTJkNjEzIiwic3ViIjoiOWQxNDNkZWMtNjY0MC00ODM3LWFiNzAtN2NkNDExYTJkNjEzIiwidGlkIjoiNDJmNzY3NmMtZjQ1NS00MjNjLTgyZjYtZGMyZDk5NzkxYWY3IiwidXRpIjoiMmdaNzZPY3Zpa3FoaGRfXzB6a2hBQSIsInZlciI6IjEuMCJ9.JbWRYtR0LMpHXAgRHO5-oV5-Y7peB5iBjIamDuz1xcH4Mmu5kYFnsd9aBIWteCXBLGyQ9Up7ig_tLRNX5CQKuHKdChc-hT1XF3hRTbsccAv35nEBuXGPViw_QjSn3bnnsTIut-FSFsmCUmnLsekPG_IWwNHpPA9M9ifmZu4GRIqyhZxl01f2Gf3buGS8jkSPs7O5UnjtmrdNzwsEjpcEQZvUnmBwdn2GfuyCU8YkxpCTN06ID_dGrGLNxHtTwnJdSB7xSCp8NA3dwd0zQJCwte6qBIgmR8nSLxCRQ9DYP1ru7DA0hfxGxsiY6DBi_P8hKldFGReAQEfgDDkC2XEtAg");
		httpPost.setHeader("Content-Type", "application/json");

		final CloseableHttpResponse resp = httpClient.execute(httpPost);
		final InputStream content = resp.getEntity().getContent();


		final Stream<String> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<String>() {

			private final ObjectMapper objectMapper = new ObjectMapper();
			private final JsonParser jParser = objectMapper.getFactory().createParser(content);
			private JsonToken currToken = jParser.nextToken();

			@Override
			public boolean hasNext() {
				return currToken != null;
			}

			@Override
			public String next() {
				return "";
			}
		}, Spliterator.ORDERED), false);


		stream.map(s -> s+"Mapped");
	}

}
