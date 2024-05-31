import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtil.class.getName());
	private static final Pattern keyPattern = Pattern.compile("[a-zA-Z_0-9\\-]+");


	/**
	 * Convert the JSONObject into a flatmap of key value pairs, with key being the flattened path in the json object.
	 *
	 * @param obj	JSONObject to convert from
	 * @param flattendProps	Storage for converted properties
	 * @param path	Path prefix, used during recursive calls
	 * @return map of attributes from arrays converted into single strings.  E.g. 5 objects in a array with 2 common properties, those 2 common properties
	 *         will be represented as single properties available outside of an array (e.g. for lists of common values)
	 * @throws JSONException
	 */
	public static Map<String, String> flattenJson(Map<String, Object> obj, Map<String, String> flattendProps, String path) throws JSONException {
		Map<String, String> keyValues = new HashMap<String, String>();
		if(obj==null)
			return keyValues;

		for(String key: obj.keySet()) {
			Object val = obj.get(key);

			if(!keyPattern.matcher(key).matches()) {
				LOGGER.debug("[JSONUtil.flattenJson] Invalid key {} - SKIPPING KEY", key);
				continue;
			}

			if(val instanceof String) {
				flattendProps.put(path + key, (String)val);
				keyValues.put(key, val.toString());
			}
			else if(val instanceof Integer) {
				flattendProps.put(path + key, ((Integer)val).toString());
				keyValues.put(key, ((Integer)val).toString());
			}
			else if(val instanceof Double) {
				flattendProps.put(path + key, ((Double)val).toString());
				keyValues.put(key, ((Double)val).toString());
			}
			else if(val instanceof Map) {
				flattenJson((Map<String, Object>)val, flattendProps, path + key + ".");
			}
			else if(val instanceof ArrayList) {
				flattenJson((ArrayList<Map<String, Object>>)val, flattendProps, path + key);
			}
			else {
				LOGGER.debug("[JSONUtil.flattenJson] {} {} ({}) - UNHANDLED TYPE", path, key, val==null ? "null" : val.getClass());
			}
		}

		return keyValues;
	}

	/**
	 * Convert the JSONArray into flattened properties.
	 *
	 * @param array	JSONArray to convert from
	 * @param flattenedProps	Storage for converted properties
	 * @param path	Path prefix, used during recursive calls
	 * @throws JSONException
	 */
	private static void flattenJson(ArrayList<Map<String, Object>> array, Map<String, String> flattenedProps, String path) throws JSONException {
		if(array==null)
			return;


		List<Map<String, String>> keyValueList = new ArrayList<Map<String, String>>();
		Map<String, String> keyValues;

		for(int i=0; i<array.size(); i++) {
			Object val = array.get(i);

			String key = "[" + i + "].";
			if(val instanceof Map) {
				keyValues = flattenJson((Map<String, Object>)val, flattenedProps, path + key);
				keyValueList.add(keyValues);
			}
			else if(val instanceof ArrayList) {
				flattenJson((ArrayList<Map<String, Object>>)val, flattenedProps, path + key);
			}
			else {
				LOGGER.debug("[JSONUtil.flattenJson] {} {} ({}) - UNHANDLED TYPE", path, key, val.getClass());
			}
		}

		flattenedProps.put(path + ".count", "" + array.size());

		// Get the unique key values
		Map<String, Set<String>> keyWithUniqueValMap = new HashMap<String, Set<String>>();
		for(int i=0; i<keyValueList.size(); i++) {
			keyValues = keyValueList.get(i);
			for(String key: keyValues.keySet()) {
				if(!keyWithUniqueValMap.containsKey(key)) {
					keyWithUniqueValMap.put(key, new HashSet<String>());
				}

				keyWithUniqueValMap.get(key).add(keyValues.get(key));
			}
		}

		// Now can go and generate the combined value
		for(String key: keyWithUniqueValMap.keySet()) {
			Iterator<String> itVals = keyWithUniqueValMap.get(key).iterator();

			String valCombined = "";
			while(itVals.hasNext()) {
				valCombined += itVals.next() + (itVals.hasNext() ? ", " : "");
			}

			flattenedProps.put(path + "." + key, valCombined);
		}
	}

	/**
	 * Gets the data from a specified path as String - the value can be either JSON or primitive value
	 * Supports only one level of array (nested array is not supported) e.g., res.arr[0].val is supported. res.arr[0][1].val is not supported
	 * @param path Path to be resolved to a JSON string
	 * @param propertyTree Property hierarchy containing all properties from the event
	 * @return value as string if property exists at given path or null
	 */
	public static String getValueAtPathAsStr(String path, Map<String, Object> propertyTree) {
		Object value = getValueAtPath(path, propertyTree);

		// Should have a value, if null then could not be found
		if(value == null) {
			LOGGER.debug("[JSONUtil.getValueAtPathAsStr] value is null");
			return null;
		}

		String valueAsStr;
		if (value instanceof Map) { // JSON object
			Map<String, Object> property = (Map<String, Object>)value;
			valueAsStr = new JSONObject(property).toString();
		} else { // Primitive (Boxed) Value like Integer, Boolean, String
			valueAsStr = value.toString();
		}

		LOGGER.debug("[JSONUtil.getValueAtPathAsStr] resulting string: " + valueAsStr);
		return valueAsStr;

	}

	/**
	 * Gets the data from a specified path
	 *
	 * @param path Path to be resolved to a JSON string
	 * @param propertyTree Property hierarchy containing all properties from the event
	 * @return value if property exists at given path or null
	 */
	public static Object getValueAtPath(String path, Map<String, Object> propertyTree) {
		if(path==null) {
			LOGGER.debug("[JSONUtil.getValueAtPath] path is null");
			return null;
		}

		String origPath = path;
		path = path.trim();
		if(path.indexOf('{')!=0 || path.indexOf('}')!=path.length()-1 || path.length()<3) {
			LOGGER.debug("[JSONUtil.getValueAtPath] path doesn't begin or end with curly brackets or has nothing between the curly brackets.  path: " + path);
			return null;
		}

		path = path.substring(1, path.length()-1);
		LOGGER.debug("[JSONUtil.getValueAtPath] Parsing path: " + path);

		// Get the path from the propertyTree
		Map<String, Object> property = propertyTree;
		Object value = null;
		String[] parts = path.split("\\.");
		Pattern arrayIndexExtract = Pattern.compile("[^\\[]*(\\[(\\d+)\\])");
		for(int i=0; i<parts.length; i++) {
			String part = parts[i];
			String partWithArrIndices = part;

			Matcher matcher = arrayIndexExtract.matcher(part);
			List<Integer> arrayIndices = new ArrayList<>();
			while (matcher.find()){
				arrayIndices.add(Integer.parseInt(matcher.group(2)));
				part = part.replace(matcher.group(1), "");
			}

			if(!property.containsKey(part)) {
				LOGGER.debug("[JSONUtil.getValueAtPath] propertyTree doesn't contain the path: \"" + origPath + "\".  Failed on part: \"" + part + "\"");
				value = null;
				break;
			}

			value = property.get(part);

			if(value instanceof Map) {
				property = (Map<String, Object>)value;
			} else if (value instanceof List) {

				int arrayLevel = 0;
				// loop over nested array indices
				for(; arrayLevel < arrayIndices.size(); arrayLevel++) {
					int arrayIndex = arrayIndices.get(arrayLevel);
					if (value instanceof  List) {
						try {
							value = ((List) value).get(arrayIndex);
						} catch (IndexOutOfBoundsException ex) {

							if (LOGGER.isDebugEnabled()) {
								StringBuilder arrWithIndicesTraversed = new StringBuilder(part); // traversed so far
								for (int k = 0; k < arrayLevel; k++) {
									arrWithIndicesTraversed.append(String.format("[%d]", arrayIndices.get(k)));
								}
								LOGGER.debug("[JSONUtil.getValueAtPath] Array Position {} is outside of property {}", arrayIndex, arrWithIndicesTraversed.toString());
							}
							value = null;
							break;
						}
					} else {
						LOGGER.debug("[JSONUtil.getValueAtPath] Property tree doesn't contain nested array at level {} as expected in property {}", arrayLevel, part);
						value = null;
						break;
					}
				}

				if (arrayLevel < arrayIndices.size()) {
					break;
				}

				if (i < parts.length - 1) { // if remaining path is there to be resolved further, ensure that value is instance of Map, otherwise break
					if (value instanceof Map) {
						property = (Map<String, Object>) value;
					} else {
						LOGGER.debug("[JSONUtil.getValueAtPath] Value of property {} expected to json object, but is a primitive value {}", partWithArrIndices, value);
						value = null;
						break;
					}
				}
			}
			else if(i+1<parts.length) { // the reached a leaf, but not done with the parts, so path doesn't match event properties
				LOGGER.debug("[JSONUtil.getValueAtPath] propertyTree doesn't contain the path: \"" + origPath + "\".  Failed on part: \"" + part + "\". Reached leaf before end of path");
				value = null;
				break;
			}
			else {
				// continue to next part
			}
		}

		LOGGER.debug("[JSONUtil.getValueAtPath] resulting json string: " + value);
		return value;
	}

	public static void main(String[] args) throws IOException {
		String jsonStr = "{\"root\":{\"key1\":\"key1\",\"key2\":{\"key3\":\"key2-key3\",\"key4\":\"key2-key4\"},\"arr1\":[\"arr1-1\",\"arr1-2\"],\"arr2\":[{\"key5\":\"arr2-1-key5\",\"key6\":\"arr2-1-key6\",\"arr2-1\":[{\"key\":\"key8\"}]},{\"key5\":\"arr2-2-key5\",\"key6\":\"arr2-2-key6\"}],\"arr3\":[[[[[[[[[\"Level 9\"]]]]]]],\"arr3-1-2\",\"arr3-1-3\"],[[{\"keyA\":\"KeyA\"},{\"KeyB\":\"KeyB\"}],\"arr3-2-2\"]]}}";
		Map<String, Object> map = new ObjectMapper().readValue(jsonStr, Map.class);

//		System.out.println(getValueAtPath("{root.arr3[0][0][0][0][0][0][0][0][0]}", map));
//		System.out.println(getValueAtPath("{root.arr2[0].arr2-1[0].key}", map));

		System.out.println(getValueAtPath("{root.arr3[1][0][0].keyA}", map));

	}

}
