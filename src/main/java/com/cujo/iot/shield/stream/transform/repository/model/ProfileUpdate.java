package com.cujo.iot.shield.stream.transform.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "profiles_update")
public class ProfileUpdate {

	private Long id;
	private String modelName;
	private String whitelist;
	private String blacklist;
	private Long timestamp;
	private Timestamp createdDate;

	public ProfileUpdate() {
	}

	private ProfileUpdate(final Long id,
	                      final String modelName,
	                      final String whitelist,
	                      final String blacklist,
	                      final Long timestamp,
	                      final Timestamp createdDate) {
		this.id = id;
		this.modelName = modelName;
		this.whitelist = whitelist;
		this.blacklist = blacklist;
		this.timestamp = timestamp;
		this.createdDate = createdDate;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}


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

	public static class Builder {
		private Long id;
		private String modelName;
		private String whitelist;
		private String blacklist;
		private Long timestamp;
		private Timestamp createdDate;

		public Builder id(final Long id) {
			this.id = id;
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

		public ProfileUpdate build() {
			return new ProfileUpdate(id, modelName, whitelist, blacklist, timestamp, createdDate);
		}
	}
}
