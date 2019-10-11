package com.cujo.iot.shield.stream.transform.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "profiles")
public class Profile {

	private String type;
	private String modelName;
	private String defaultPolicy;
	private String whitelist;
	private String blacklist;
	private Long timestamp;
	private Timestamp createdDate;

	public Profile() {
	}

	public Profile(final String type,
			       final String modelName,
	               final String defaultPolicy,
	               final String whitelist,
	               final String blacklist,
	               final Long timestamp,
	               final Timestamp createdDate,
	               final Timestamp modifiedDate) {
		this.type = type;
		this.modelName = modelName;
		this.defaultPolicy = defaultPolicy;
		this.whitelist = whitelist;
		this.blacklist = blacklist;
		this.timestamp = timestamp;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	private Timestamp modifiedDate;

	@Id
	public String getModelName() {
		return modelName;
	}

	public void setModelName(final String modelName) {
		this.modelName = modelName;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final Long timestamp) {
		this.timestamp = timestamp;
	}

	@Column(name = "CreatedDate", updatable=false)
	@CreationTimestamp
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "ModifiedDate")
	@UpdateTimestamp
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(final Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(updatable = false)
	public String getDefaultPolicy() {
		return defaultPolicy;
	}

	public void setDefaultPolicy(final String defaultPolicy) {
		this.defaultPolicy = defaultPolicy;
	}

	public String getWhitelist() {
		return whitelist;
	}

	public void setWhitelist(final String whitelist) {
		this.whitelist = whitelist;
	}

	public String getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(final String blacklist) {
		this.blacklist = blacklist;
	}

	public static Builder builder() {
		return new Builder();
	}

	@Transient
	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public static class Builder {

		private String type;
		private String modelName;
		private String defaultPolicy;
		private String whitelist;
		private String blacklist;
		private Long timestamp;
		private Timestamp createdDate;
		private Timestamp modifiedDate;

		public Builder defaultPolicy(final String defaultPolicy) {
			this.defaultPolicy = defaultPolicy;
			return this;
		}

		public Builder whitelist(final String whitelist) {
			this.whitelist = whitelist;
			return this;
		}

		public Builder blacklist(final String blacklist) {
			this.blacklist = blacklist;
			return this;
		}

		public Builder modelName(final String modelName) {
			this.modelName = modelName;
			return this;
		}

		public Builder timestamp(final Long timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder createdDate(final Timestamp createdDate) {
			this.createdDate = createdDate;
			return this;
		}

		public Builder modifiedDate(final Timestamp modifiedDate) {
			this.modifiedDate = modifiedDate;
			return this;
		}

		public Builder type(final String type) {
			this.type = type;
			return this;
		}

		public Profile build() {
			return new Profile(type,
					           modelName,
			                   defaultPolicy,
			                   whitelist,
			                   blacklist,
			                   timestamp,
			                   createdDate,
			                   modifiedDate);
		}
	}
}
