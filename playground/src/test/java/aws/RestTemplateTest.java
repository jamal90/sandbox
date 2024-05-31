package aws;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testPutCall() {

		final RestTemplate restTemplate = new RestTemplate();
		final ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("name", "TestRouter-Test1");

		String resourceUrl = "https://d028a17c-8ed4-499c-a914-f22d1a8abf0f.eu10.cp.iot.sap/iot/core/api/v1/devices/5";

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("content-type", "");
		httpHeaders.add("Authorization", "Basic cm9vdDpCWkNZMFBvNjJJQWMyT2o=");

		HttpEntity<String> reqEntity = new HttpEntity<>(objectNode.toString(), httpHeaders);

		final ResponseEntity<JsonNode> exchange = restTemplate.exchange(resourceUrl, HttpMethod.PUT, reqEntity, JsonNode.class);

		System.out.println("Result: " + exchange.getBody());
	}
}
