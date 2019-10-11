package com.cujo.iot.shield.stream.transform.repository.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * RequestDTO.
 */
@Entity
@Table(name = "Responses")
public class Response implements Serializable {
	private static final long serialVersionUID = 1L;

	public Response() {
	}

	@Id
	@GeneratedValue
	private Long id;
	private String type;
	private String requestId;
	private String deviceId;

	private Response(final Long id, final String type, final String requestId, final String deviceId, final String action) {
		this.id = id;
		this.type = type;
		this.requestId = requestId;
		this.deviceId = deviceId;
		this.action = action;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Response)) {
			return false;
		}
		final Response that = (Response) o;
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

		private Long id;
		private String type;
		private String requestId;
		private String deviceId;
		private String action;

		public Builder id(final Long id) {
			this.id = id;
			return this;
		}
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

		public Response build() {
			return new Response(id, type, requestId, deviceId, action);
		}
	}
}
