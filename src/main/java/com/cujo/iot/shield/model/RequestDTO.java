package com.cujo.iot.shield.model;

import java.io.Serializable;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RequestDTO.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("type")
	private String type;

	@JsonProperty("request_id")
	private String requestId;

	@JsonProperty("model_name")
	private String modelName;

	@JsonProperty("device_id")
	private String deviceId;

	@JsonProperty("url")
	private String url;

	@JsonProperty("timestamp")
	private Long timestamp;

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(final String requestId) {
		this.requestId = requestId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(final String modelName) {
		this.modelName = modelName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(final String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof RequestDTO)) {
			return false;
		}
		final RequestDTO that = (RequestDTO) o;
		return type.equals(that.type) &&
				requestId.equals(that.requestId) &&
				modelName.equals(that.modelName) &&
				deviceId.equals(that.deviceId) &&
				Objects.equals(url, that.url) &&
				timestamp.equals(that.timestamp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, requestId, modelName, deviceId, url, timestamp);
	}
}
