package com.cujo.iot.shield.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Profile DTO.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("type")
	private String type;
	@JsonProperty("model_name")
	private String modelName;
	@JsonProperty("default")
	private String defaultPolicy;
	@JsonProperty("whitelist")
	private List<String> whitelist = new ArrayList<>();
	@JsonProperty("blacklist")
	private List<String> blacklist = new ArrayList<>();
	@JsonProperty("timestamp")
	private Long timestamp;

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(final String modelName) {
		this.modelName = modelName;
	}

	public String getDefaultPolicy() {
		return defaultPolicy;
	}

	public void setDefaultPolicy(final String defaultPolicy) {
		this.defaultPolicy = defaultPolicy;
	}

	public List<String> getWhitelist() {
		return whitelist;
	}

	public void setWhitelist(final List<String> whitelist) {
		this.whitelist = whitelist;
	}

	public List<String> getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(final List<String> blacklist) {
		this.blacklist = blacklist;
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
		if (!(o instanceof ProfileDTO)) {
			return false;
		}
		final ProfileDTO that = (ProfileDTO) o;
		return type.equals(that.type) &&
				modelName.equals(that.modelName) &&
				defaultPolicy.equals(that.defaultPolicy) &&
				Objects.equals(whitelist, that.whitelist) &&
				Objects.equals(blacklist, that.blacklist) &&
				timestamp.equals(that.timestamp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, modelName, defaultPolicy, whitelist, blacklist, timestamp);
	}
}
