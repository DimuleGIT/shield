package com.cujo.iot.shield.model;

import java.io.Serializable;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RequestDTO.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("type")
	private String type;
	@JsonProperty("request_id")
	private String requestId;
	@JsonProperty("device_id")
	private String deviceId;

	private ResponseDTO(final String type, final String requestId, final String deviceId, final String action) {
		this.type = type;
		this.requestId = requestId;
		this.deviceId = deviceId;
		this.action = action;
	}

	@JsonProperty("action")
	private String action;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(final String requestId) {
		this.requestId = requestId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(final String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ResponseDTO)) {
			return false;
		}
		final ResponseDTO that = (ResponseDTO) o;
		return type.equals(that.type) &&
				requestId.equals(that.requestId) &&
				deviceId.equals(that.deviceId) &&
				action.equals(that.action);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, requestId, deviceId, action);
	}

	public String getAction() {
		return action;
	}

	public void setAction(final String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String type;
		private String requestId;
		private String deviceId;
		private String action;

		public Builder type(final String type) {
			this.type = type;
			return this;
		}

		public Builder requestId(final String requestId) {
			this.requestId = requestId;
			return this;
		}

		public Builder deviceId(final String deviceId) {
			this.deviceId = deviceId;
			return this;
		}

		public Builder action(final String action) {
			this.action = action;
			return this;
		}

		public ResponseDTO build() {
			return new ResponseDTO(type, requestId, deviceId, action);
		}
	}
}
