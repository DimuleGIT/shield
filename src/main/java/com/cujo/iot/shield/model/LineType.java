package com.cujo.iot.shield.model;

import java.util.Arrays;

public enum LineType {
	REQUEST ("request"),
	PROFILE_CREATE ("profile_create"),
	PROFILE_UPDATE ("profile_update");

	private String name;

	public String getName() {
		return name;
	}

     LineType(final String name) {
	    this.name = name;
	}

	public static LineType fromValue(final String name) {
		return Arrays.stream(LineType.values())
				.filter(lineType -> String.valueOf(lineType.name).equals(name))
				.findFirst()
				.orElse(null);
	}
}
