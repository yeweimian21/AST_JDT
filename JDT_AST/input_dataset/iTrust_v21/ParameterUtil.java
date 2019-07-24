package edu.ncsu.csc.itrust;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Provides a utility method that converts the "Map" from the JSP container to a type-checked hashmap
 */
public class ParameterUtil {
	/**
	 * Provides a utility method that converts the "Map" from the JSP container to a type-checked hashmap
	 * @param params Map to convert
	 * @return converted Map
	 */
	public static HashMap<String, String> convertMap(Map<?, ?> params) {
		HashMap<String, String> myMap = new HashMap<String, String>();
		for (Entry<?, ?> entry : params.entrySet()) {
			String[] value = ((String[]) entry.getValue());
			if (value != null)
				myMap.put(entry.getKey().toString(), value[0]);
			else
				myMap.put(entry.getKey().toString(), null);
		}
		return myMap;
	}
}
