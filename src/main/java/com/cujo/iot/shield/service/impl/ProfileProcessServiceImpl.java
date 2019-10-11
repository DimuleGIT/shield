package com.cujo.iot.shield.service.impl;

import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cujo.iot.shield.model.ProfileDTO;
import com.cujo.iot.shield.service.ProfileProcessService;
import com.cujo.iot.shield.service.ProfileService;
import com.cujo.iot.shield.stream.transform.ProfileTransformer;
import com.cujo.iot.shield.util.JsonUtils;

@Service
public class ProfileProcessServiceImpl implements ProfileProcessService {

	private static final String ERROR_WHILE_DESERIALIZE_PROFILE_DTO = "Error while deserialize profileDTO";
	private ProfileTransformer profileTransformer;
	private ProfileService profileService;

	private static final Logger log = LoggerFactory.getLogger(ProfileProcessServiceImpl.class);

	@Autowired
	public ProfileProcessServiceImpl(final ProfileTransformer profileTransformer, final ProfileService profileService) {
		this.profileTransformer = profileTransformer;
		this.profileService = profileService;
	}

	@Override
	public void process(final String streamLine) {
		Optional.ofNullable(getProfileDTO(streamLine))
				.map(profileTransformer::transform)
				.ifPresent(profileService::saveProfile);

	}

	private ProfileDTO getProfileDTO(final String streamLine) {
		return Optional.ofNullable(streamLine)
				.map(this::deserializeProfileDTO)
				.orElse(null);
	}

	private ProfileDTO deserializeProfileDTO(final String streamLine) {
		try {
			return JsonUtils.deserialize(streamLine, ProfileDTO.class);
		} catch (IOException e) {
			log.trace(ERROR_WHILE_DESERIALIZE_PROFILE_DTO, e);
			return null;
		}
	}
}
