package com.cujo.iot.shield.util;

import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Jackson backed JSON serialization utils.
 */
public final class JsonUtils {
	private static final ObjectMapper MAPPER = new ObjectMapper()
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	public static <T> T deserialize(final String value, final Class<T> clazz) throws IOException {
		return MAPPER.readValue(value, clazz);
	}

	public static <T> T deserialize(final String value, final TypeReference valueTypeRef) throws IOException {
		return MAPPER.readValue(value, valueTypeRef);
	}

	public static String serialize(final Object objectToSerialize) throws IOException {
		return MAPPER.writeValueAsString(objectToSerialize);
	}

	/**
	 * Default constructor.
	 */
	private JsonUtils() {
		super();
	}
}
