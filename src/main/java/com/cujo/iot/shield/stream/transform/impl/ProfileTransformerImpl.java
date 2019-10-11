package com.cujo.iot.shield.stream.transform.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.cujo.iot.shield.model.ProfileDTO;
import com.cujo.iot.shield.stream.transform.ProfileTransformer;
import com.cujo.iot.shield.stream.transform.repository.model.Profile;
import com.cujo.iot.shield.stream.transform.repository.model.ProfileUpdate;

@Service
public class ProfileTransformerImpl implements ProfileTransformer {

	private static final String DELIMITER = ",";

	@Override
	public Profile transform(final ProfileDTO profileDTO) {
		return Profile.builder()
				.type(profileDTO.getType())
				.whitelist(getStringFromList(profileDTO.getWhitelist()))
				.blacklist(getStringFromList(profileDTO.getBlacklist()))
				.defaultPolicy(profileDTO.getDefaultPolicy())
				.modelName(profileDTO.getModelName())
				.timestamp(profileDTO.getTimestamp())
				.build();
	}

	@Override
	public ProfileUpdate transform(final Profile profile) {
		return ProfileUpdate.builder()
				.whitelist(profile.getWhitelist())
				.blacklist(profile.getBlacklist())
				.modelName(profile.getModelName())
				.timestamp(profile.getTimestamp())
				.build();
	}

	private String getStringFromList(final List<String> listOfString) {
		return Optional.ofNullable(listOfString)
		          .map(list -> String.join(DELIMITER, list))
				  .orElse(null);
	}
}
