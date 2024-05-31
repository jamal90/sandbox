package kafka;

import org.junit.Test;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class KafkaUtils {

	@Test
	public void generateMessages() throws InterruptedException {


		int numberOfMessages = 5;
		SampleKafkaProducer kafkaProducer = new SampleKafkaProducer();

		AtomicInteger correctMsgs = new AtomicInteger(0);

		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicInteger messageSent = new AtomicInteger(0);

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		Instant then = Instant.now();
		String topic = "fe505fcc-46ef-4c71-a0af-f4858e1bb301.sap.iot.timeseries.raw_c2c_interop-integration.v1";  // actions tenant
//		String topic = "fd7d1261-3e73-4652-b8b1-245da32ecc53.sap.iot.timeseries.raw_c2c_interop.v1";

		executorService.scheduleAtFixedRate(() -> {

			String deviceId = "ITCarAlt09585808";
//			String deviceId = "test-s1-" + new Random().nextInt(20);

			String msg = "{" +
					"  \"capabilityId\": \"cadcb1fb-a3cf-4181-868d-8ad484277269\"," +
					"  \"sensorId\": \"1e425c00-abaf-4736-ad25-bd3526821a68\"," +
					"  \"timestamp\": 1488896008000," +
					"  \"measures\": [" +
					"    {" +
					"      \"interopDeviceId\": \"" + deviceId + "\"," +
					"      \"payload\": \"{" +
					"          \\\"_time\\\":\\\""+Instant.now().toString()+"\\\"," +
					"          \\\"temp\\\":" + new Random().nextInt(100) + "," +
					"          \\\"humidity\\\":"+ new Random().nextInt(100) +
					"        }\"" +
					"    }" +
					"  ]" +
					"}";

			/*if (new Random().nextInt(10) % 2 == 0) {

				correctMsgs.incrementAndGet();
				msg = "{" +
						"  \"capabilityId\": \"cadcb1fb-a3cf-4181-868d-8ad484277269\"," +
						"  \"sensorId\": \"1e425c00-abaf-4736-ad25-bd3526821a68\"," +
						"  \"timestamp\": 1488896008000," +
						"  \"measures\": [" +
						"    {" +
						"      \"hyperscalerDeviceId\": \"" + deviceId + "\"," +
						"      \"payload\": \"{" +
//					"          \\\"_time\\\":\\\""+Instant.now().toString()+"\\\"," +
						"          \\\"_time\\\":\\\""+Instant.now().toString()+"\\\"," +
						"          \\\"Temperature\\\":" + new Random().nextInt(100) + "," +
						"          \\\"filllevel\\\":"+ new Random().nextInt(100) +
						"        }\"" +
						"    }" +
						"  ]" +
						"}";
			}*/
			
			try {
//				String topic = "topic-"+ (new Random().nextInt(5)) +".sap.iot.timeseries.raw_hyperscaler_beta.v1";
//				System.out.println("Sending message - " + msg);
				kafkaProducer.sendRecord(topic, msg);
			} catch (ExecutionException|InterruptedException e) {
				e.printStackTrace();
			}

			if (messageSent.incrementAndGet() >= numberOfMessages) {
				executorService.shutdown();
				latch.countDown();
			}
		}, 0, 10, TimeUnit.MILLISECONDS);

		latch.await();

		System.out.println("No. of correctMsgs: " + correctMsgs);
	}

	@Test
	public void testString() {

		String v1 = "{\"capabilityAlternateId\":\"_interopCapability\",\"sensorAlternateId\":\"_interopRouterSensor\",\"measureMessageId\":\"perf-test-s1-0-1556923242352\",\"processingTag\":\"perf-test-s1-0\",\"measures\":[{\"payload\":\"{\\\"temp\\\":22.442356728014268,\\\"humidity\\\":1.0,\\\"torque\\\":0.018686205761060126,\\\"speed\\\":2.130561840345379,\\\"_time\\\":\\\"2019-05-03T22:39:26.155Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.85426784732307,\\\"humidity\\\":1.0,\\\"torque\\\":0.06176014646584442,\\\"speed\\\":1.8208808103947172,\\\"_time\\\":\\\"2019-05-03T22:39:26.354Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.442356728014268,\\\"humidity\\\":1.0,\\\"torque\\\":0.018686205761060126,\\\"speed\\\":2.130561840345379,\\\"_time\\\":\\\"2019-05-03T22:39:26.155Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.85426784732307,\\\"humidity\\\":1.0,\\\"torque\\\":0.06176014646584442,\\\"speed\\\":1.8208808103947172,\\\"_time\\\":\\\"2019-05-03T22:39:26.354Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.442356728014268,\\\"humidity\\\":1.0,\\\"torque\\\":0.018686205761060126,\\\"speed\\\":2.130561840345379,\\\"_time\\\":\\\"2019-05-03T22:39:26.155Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.85426784732307,\\\"humidity\\\":1.0,\\\"torque\\\":0.06176014646584442,\\\"speed\\\":1.8208808103947172,\\\"_time\\\":\\\"2019-05-03T22:39:26.354Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.442356728014268,\\\"humidity\\\":1.0,\\\"torque\\\":0.018686205761060126,\\\"speed\\\":2.130561840345379,\\\"_time\\\":\\\"2019-05-03T22:39:26.155Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.85426784732307,\\\"humidity\\\":1.0,\\\"torque\\\":0.06176014646584442,\\\"speed\\\":1.8208808103947172,\\\"_time\\\":\\\"2019-05-03T22:39:26.354Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.442356728014268,\\\"humidity\\\":1.0,\\\"torque\\\":0.018686205761060126,\\\"speed\\\":2.130561840345379,\\\"_time\\\":\\\"2019-05-03T22:39:26.155Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.85426784732307,\\\"humidity\\\":1.0,\\\"torque\\\":0.06176014646584442,\\\"speed\\\":1.8208808103947172,\\\"_time\\\":\\\"2019-05-03T22:39:26.354Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.943580300653593,\\\"humidity\\\":1.0,\\\"torque\\\":0.05933006078293519,\\\"speed\\\":1.4995099062887915,\\\"_time\\\":\\\"2019-05-03T22:39:26.563Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.667160836796516,\\\"humidity\\\":1.0,\\\"torque\\\":0.18845392429026336,\\\"speed\\\":1.737391305513619,\\\"_time\\\":\\\"2019-05-03T22:39:26.766Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":22.994371472144337,\\\"humidity\\\":1.0,\\\"torque\\\":0.04045396641132957,\\\"speed\\\":1.7364837677246192,\\\"_time\\\":\\\"2019-05-03T22:39:26.977Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"},{\"payload\":\"{\\\"temp\\\":23.578890877198173,\\\"humidity\\\":4.0,\\\"torque\\\":0.7602798040410027,\\\"speed\\\":2.75766185840416,\\\"_time\\\":\\\"2019-05-03T22:39:27.189Z\\\"}\",\"interopDeviceId\":\"perf-test-s1-0\"}]}";
		System.out.println(v1.getBytes().length);
	}
}
