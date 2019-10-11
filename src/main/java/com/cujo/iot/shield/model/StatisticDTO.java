package com.cujo.iot.shield.model;

import java.io.Serializable;

/**
 * Statistic DTO.
 */
public class StatisticDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type;
	private Integer value;

	private StatisticDTO(final String type, final Integer value) {
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(final Integer value) {
		this.value = value;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String type;
		private Integer value;


		public Builder type(final String type) {
			this.type = type;
			return this;
		}

		public Builder value(final Integer value) {
			this.value = value;
			return this;
		}

		public StatisticDTO build() {
			return new StatisticDTO(type, value);
		}
	}
}