package com.jonandvirginia.small.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtil {

	public static final ObjectMapper MAPPER = new ObjectMapper();
	static {
		MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	private MapperUtil() { } // Don't instantiate
	
	
}
