package streams;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ADXStreamingClientV2 {


	public static void main(String[] args) throws IOException {


		final CloseableHttpClient httpClient = HttpClients.createDefault();

		final HttpPost httpPost = new HttpPost("https://adxtimeseriesstore.westus2.kusto.windows.net/v2/rest/query");

		final StringEntity stringEntity = new StringEntity("{" +
				"  \"db\":\"timeseries-db-2\"," +
				"  \"csl\":\"set notruncation; IG2 | take 10000\"," +
				"  \"properties\":\"{\\\"Options\\\":{\\\"queryconsistency\\\":\\\"strongconsistency\\\", \\\"results_progressive_enabled\\\": \\\"true\\\", \\\"query_results_progressive_row_count\\\": 100},\\\"Parameters\\\":{},\\\"ClientRequestId\\\":\\\"MyApp.Query;e9f884e4-90f0-404a-8e8b-01d883023bf1\\\"}\"" +
				"}", StandardCharsets.UTF_8);

		httpPost.setEntity(stringEntity);
		httpPost.setHeader("Authorization", "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IkhsQzBSMTJza3hOWjFXUXdtak9GXzZ0X3RERSIsImtpZCI6IkhsQzBSMTJza3hOWjFXUXdtak9GXzZ0X3RERSJ9.eyJhdWQiOiJodHRwczovL2FkeHRpbWVzZXJpZXNzdG9yZS53ZXN0dXMyLmt1c3RvLndpbmRvd3MubmV0IiwiaXNzIjoiaHR0cHM6Ly9zdHMud2luZG93cy5uZXQvNDJmNzY3NmMtZjQ1NS00MjNjLTgyZjYtZGMyZDk5NzkxYWY3LyIsImlhdCI6MTU4MTc4ODAzMSwibmJmIjoxNTgxNzg4MDMxLCJleHAiOjE1ODE3OTE5MzEsImFpbyI6IjQyTmdZQWdzMjMvdkc3dk5pNTZVVS8zMkVScFNBQT09IiwiYXBwaWQiOiJjZTNmNDM0NS0wMTQ1LTQ3YjQtYTAxMS1jZWJmMzE0YTJiODQiLCJhcHBpZGFjciI6IjEiLCJpZHAiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80MmY3Njc2Yy1mNDU1LTQyM2MtODJmNi1kYzJkOTk3OTFhZjcvIiwib2lkIjoiOWQxNDNkZWMtNjY0MC00ODM3LWFiNzAtN2NkNDExYTJkNjEzIiwic3ViIjoiOWQxNDNkZWMtNjY0MC00ODM3LWFiNzAtN2NkNDExYTJkNjEzIiwidGlkIjoiNDJmNzY3NmMtZjQ1NS00MjNjLTgyZjYtZGMyZDk5NzkxYWY3IiwidXRpIjoiaTc4Z3g0eTEyRTJQLW5ZVXA4NFVBQSIsInZlciI6IjEuMCJ9.QHGocPzzCtBuH8EnrbYiJCy1_T7p5nmSFDfV7WY5GQkHWVCDMpy4y01oVh2nFlLb2daioBu1JemrIsf9toNzdVxcrc3zyTFiGD8bytfMe8pkowV36Z6nDCJ10konEAw1f1mAKgbzraVFpAeM9MMKl-nN8cEUhgcTpv3N2ZP8_ZX3q3rWClHpAY6PnMjPVv4Rb-NKQ4urReJc6etE6-Z2DbdRP8UzS3fMcloHqDgdich0vgInPLFAJmf5Ca8jszWz3D5mgeSHyQMYQmvSlF6D7_vf1SsqVVrghkBhgIJbURugw-2EH0dsqqZWdXa4mjhfUiLJBSInm6Qy2MTZkJ6O3g");
		httpPost.setHeader("Content-Type", "application/json");

		final CloseableHttpResponse resp = httpClient.execute(httpPost);
		final InputStream content = resp.getEntity().getContent();

		ObjectMapper objectMapper = new ObjectMapper();
		final JsonParser jParser = objectMapper.getFactory().createParser(content);

		// create new StreamingResult object and then set the values accordingly

		final Stream<List<List<String>>> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<List<List<String>>>() {

			private JsonToken jsonToken = jParser.nextToken();

			@Override
			public boolean hasNext() {
				return jsonToken != null;
			}

			@Override
			public List<List<String>> next() {

				TreeNode nextTableFragment = null;

				try {

					while (this.jsonToken != null && nextTableFragment == null) {

						// first frame identified
						if(this.jsonToken == JsonToken.FIELD_NAME && "FrameType".equals(jParser.getCurrentName())) {
							jParser.nextToken();
							final String frameType = jParser.getValueAsString();

							if (!"TableFragment".equals(frameType)) continue;

							// go forward until start of array
							while(jParser.nextToken() != null && jParser.getCurrentToken() != JsonToken.START_ARRAY) continue;

							nextTableFragment = objectMapper.readTree(jParser);

							// System.out.println("Fragment - " + counter.getAndIncrement() + " --- " + treeNode.toString());
						}

						this.jsonToken = jParser.nextToken();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				List<List<String>> nextRows = new ArrayList<>();

				if (nextTableFragment != null) {
					if (nextTableFragment.isArray()) {

						final ArrayNode fragmentRows = (ArrayNode) nextTableFragment;
						
						for (JsonNode row : fragmentRows) {
							List<String> rowVector = new ArrayList<>();
							if (row.isArray()) {
								for (JsonNode value : row) {
									// todo: check if value null
									rowVector.add(value.toString());
								}
								nextRows.add(rowVector);
							}
						}
					}
				}

				return nextRows;
			}
		}, Spliterator.ORDERED), false);

		System.out.println(stream.flatMap(Collection::stream)
			.peek(System.out::println)
				.count());
	}
}
