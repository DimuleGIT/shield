package com.cujo.iot.shield.stream.transform.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "IotRequests")
public class IotRequest {

	private String requestId;
	private String deviceId;
	private String modelName;
	private String url;
	private Long timestamp;
	private Date createdDate;

	public IotRequest(final String requestId, final String deviceId, final String modelName, final String url, final Long timestamp,
	                  final Date createdDate) {
		this.requestId = requestId;
		this.deviceId = deviceId;
		this.modelName = modelName;
		this.url = url;
		this.timestamp = timestamp;
		this.createdDate = createdDate;
	}

	public IotRequest() {
	}

	@Id
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

	public String getModelName() {
		return modelName;
	}

	public void setModelName(final String modelName) {
		this.modelName = modelName;
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

	@Column(name = "CreatedDate", updatable=false)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String requestId;
		private String deviceId;
		private String modelName;
		private String url;
		private Long timestamp;
		private Date createdDate;

		public Builder requestId(final String requestId) {
			this.requestId = requestId;
			return this;
		}

		public Builder deviceId(final String deviceId) {
			this.deviceId = deviceId;
			return this;
		}

		public Builder modelName(final String modelName) {
			this.modelName = modelName;
			return this;
		}

		public Builder url(final String url) {
			this.url = url;
			return this;
		}

		public Builder timestamp(final Long timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder createdDate(final Date createdDate) {
			this.createdDate = createdDate;
			return this;
		}

		public IotRequest build() {
			return new IotRequest(requestId, deviceId, modelName, url, timestamp, createdDate);
		}
	}

}
