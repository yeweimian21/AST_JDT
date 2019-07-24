package edu.ncsu.csc.itrust.unit;

import java.util.HashMap;
import java.util.Map;

import edu.ncsu.csc.itrust.ParameterUtil;
import junit.framework.TestCase;

public class ParameterUtilTest extends TestCase {
	@SuppressWarnings("unchecked")
	public void testConvertMap() throws Exception {
		@SuppressWarnings("rawtypes")
		Map paramMap = new HashMap();
		paramMap.put("param1", new String[] { "a" });
		paramMap.put("param2", new String[] { null });
		paramMap.put("param3", null);
		HashMap<String, String> convertedMap = ParameterUtil.convertMap(paramMap);
		assertEquals(3, convertedMap.entrySet().size());
		assertEquals("a", convertedMap.get("param1"));
		assertNull(convertedMap.get("param2"));
		assertNull(convertedMap.get("param3"));
	}
}
