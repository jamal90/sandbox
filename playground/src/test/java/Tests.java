import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import sun.jvm.hotspot.debugger.cdbg.BaseClass;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.Mockito.*;

public class Tests {

	@Test
	public void test() {


		String s1 = "[{\"PropertyMeasures\":{\"results\":[{\"StructurePropertyId\":\"C949D723F6D340F896BC5AE882897777\",\"CapabilityPropertyId\":\"C949D723F6D340F896BC5AE882897777\"},{\"StructurePropertyId\":\"D736C169G4A219B170AN2AE662592222\",\"CapabilityPropertyId\":\"D736C169G4A219B170AN2AE662592222\"}]}},{\"sensorId\":\"384109E0F2534A6A382501/FC3BE38F63334009958761B997859710\",\"objectId\":\"384109E0F2534A6A382501\",\"structureId\":\"E10100304AEFE7A616005E02C64AE811\",\"sourceId\":\"06f1eb701a334b429513da946cb1ed57\",\"tenant\":\"fd7d1261-3e73-4652-b8b1-245da32ecc53\",\"tag\":[{\"tagValue\":\"384109E0F2534A6A382501\",\"tagSemantic\":\"equipmentId\"},{\"tagValue\":\"FC3BE38F63334009958761B997859710\",\"tagSemantic\":\"modelId\"},{\"tagValue\":\"E10100304AEFE7A616005E02C64AE811\",\"tagSemantic\":\"indicatorGroupId\"}]},{\"type\":\"record\",\"name\":\"E10100304AEFE7A616005E02C64AE811\",\"fields\":[{\"name\":\"messageId\",\"type\":\"string\"},{\"name\":\"indentifier\",\"type\":\"string\"},{\"name\":\"tenant\",\"type\":\"string\"},{\"name\":\"tags\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"queryParams\",\"fields\":[{\"name\":\"modelId\",\"type\":[\"null\",\"string\"]},{\"name\":\"indicatorGroupId\",\"type\":[\"null\",\"string\"]},{\"name\":\"templateId\",\"type\":[\"null\",\"string\"]},{\"name\":\"equipmentId\",\"type\":[\"null\",\"string\"]}]}}},{\"name\":\"measurements\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"timeseriesRecord\",\"fields\":[{\"name\":\"_time\",\"type\":{\"type\":\"long\",\"logicalType\":\"nTimestamp\"}},{\"name\":\"C949D723F6D340F896BC5AE882897777\",\"type\":[\"null\",\"int\"]},{\"name\":\"D736C169G4A219B170AN2AE662592222\",\"type\":[\"null\",{\"type\":\"bytes\",\"logicalType\":\"nDecimal\",\"scale\":2,\"precision\":10}]}]}}}]}]";
		System.out.println((s1.getBytes(StandardCharsets.UTF_8).length / 1024.0) + " KB");

		// typically for all metadata max 8KB - info per device
		String s2 = "[{\"PropertyMeasures\":{\"results\":[{\"StructurePropertyId\":\"C949D723F6D340F896BC5AE882897777\",\"CapabilityPropertyId\":\"C949D723F6D340F896BC5AE882897777\"},{\"StructurePropertyId\":\"D736C169G4A219B170AN2AE662592222\",\"CapabilityPropertyId\":\"D736C169G4A219B170AN2AE662592222\"}]}},{\"sensorId\":\"384109E0F2534A6A382501/FC3BE38F63334009958761B997859710\",\"objectId\":\"384109E0F2534A6A382501\",\"structureId\":\"E10100304AEFE7A616005E02C64AE811\",\"sourceId\":\"06f1eb701a334b429513da946cb1ed57\",\"tenant\":\"fd7d1261-3e73-4652-b8b1-245da32ecc53\",\"tag\":[{\"tagValue\":\"384109E0F2534A6A382501\",\"tagSemantic\":\"equipmentId\"},{\"tagValue\":\"FC3BE38F63334009958761B997859710\",\"tagSemantic\":\"modelId\"},{\"tagValue\":\"E10100304AEFE7A616005E02C64AE811\",\"tagSemantic\":\"indicatorGroupId\"}]}]";

		// without schema
		System.out.println((s2.getBytes(StandardCharsets.UTF_8).length / 1024.0) + " KB");
	}

	@Test
	public void testStringMatcher() {
		String message = "ClientRequestId=123123123";
		Pattern pattern = Pattern.compile("ClientRequestId=[^,]*");

		String res = "";
		if (pattern != null && message != null) {
			Matcher matcher = pattern.matcher(message);
			if (matcher.find()) {
				res = matcher.group();
			}
		}

		System.out.println(res);
	}

	@Test
	public void testParseBoolean() {
		System.out.println(Boolean.parseBoolean("TRUE"));
	}


	@Test
	public void testAvroStatus() {
		Boolean status = null;

		if (status != true) {
			System.out.println("hello");
		} else {
			System.out.println("Bye!");
		}
	}

	@Test
	public void testJulLogging() {
		String path = Tests.class
				.getClassLoader()
				.getResource("logging.properties")
				.getFile();
		System.setProperty("java.util.logging.config.file", path);

		Logger test = Logger.getLogger("test");

		test.log(Level.SEVERE, "Hello");

		String[] arr = new String[]{"a", "b"};
		logEntering(test, arr);
	}

	private <T> void logEntering(Logger test, T val) {
		test.entering(this.getClass().getSimpleName(), "testJulLogging", val);
	}


	static class Base {
		public int testFunc(int a) {
			return 5;
		}
	}

	static class SubClass extends Base{
		@Override
		public int testFunc(int a) {
			return super.testFunc(a) + a;

		}
	}

	@Test
	public void testBaseClassMock() {
		SubClass cut = spy(new SubClass());

		Mockito.doReturn(100).when((Base) cut).testFunc(anyInt());
		System.out.println(cut.testFunc(5));
	}

	@Test
	public void testInstantParse() {
		DateTimeFormatter dtf = new DateTimeFormatterBuilder()
				.appendPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS")
				.toFormatter()
				.withZone(ZoneId.of(ZoneOffset.UTC.getId()));

		Instant parse = dtf.parse("2020-08-14 18:06:32.8720000", Instant::from);
		System.out.println(parse.toString());
		System.out.println(parse.toEpochMilli());
	}

	@Test
	public void testDateFormat() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = sdf.parse("00:10:00");
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		Integer visibilityTimeout = calendar.get(Calendar.SECOND);

		System.out.println(visibilityTimeout);

//		Duration.between(LocalTime.MIN, LocalTime.parse("00:10:00")).getSeconds()
	}

	@Test
	public void testObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		POJO hello = new POJO(1, "hello");
		Map<String, Object> pojoMap = objectMapper.convertValue(hello, new TypeReference<Map<String, Object>>() {
		});

		System.out.println(pojoMap);
	}

	public static class POJO {
		private int prop1;
		private String prop2;

		public POJO(int prop1, String prop2) {
			this.prop1 = prop1;
			this.prop2 = prop2;
		}

		public POJO() {

		}

		public int getProp1() {
			return prop1;
		}

		public void setProp1(int prop1) {
			this.prop1 = prop1;
		}

		public String getProp2() {
			return prop2;
		}

		public void setProp2(String prop2) {
			this.prop2 = prop2;
		}
	}

}
