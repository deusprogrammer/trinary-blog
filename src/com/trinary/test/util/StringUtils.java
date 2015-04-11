package com.trinary.test.util;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {
	public static Map<String, String> stringToMap(String string, String pairSeperator, String kvSeperator) {
		Map<String, String> map = new HashMap<String, String>();
		String[] pairs = string.split(pairSeperator);
		for (String pair : pairs) {
			String[] kv = pair.split(kvSeperator);
			if (kv.length == 2) {
				map.put(kv[0].trim(), kv[1].trim());
			}
		}
		
		return map;
	}
}
