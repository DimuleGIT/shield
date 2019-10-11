package com.cujo.iot.shield.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cujo.iot.shield.model.LineType;
import com.cujo.iot.shield.service.ProfileService;
import com.cujo.iot.shield.service.ProfileUpdateService;
import com.cujo.iot.shield.stream.transform.ProfileTransformer;
import com.cujo.iot.shield.stream.transform.repository.ProfileRepository;
import com.cujo.iot.shield.stream.transform.repository.model.Profile;

@Service
public class ProfileServiceImpl implements ProfileService {
	private ProfileRepository profileRepository;
	private ProfileUpdateService profileUpdateService;
	private ProfileTransformer profileTransformer;

	@Autowired
	public ProfileServiceImpl(final ProfileRepository profileRepository,
	                          final ProfileUpdateService profileUpdateService,
	                          final ProfileTransformer profileTransformer) {
		this.profileRepository = profileRepository;
		this.profileUpdateService = profileUpdateService;
		this.profileTransformer = profileTransformer;
	}

	@Override
	public List<Profile> findAll() {
		return profileRepository.findAll();
	}

	@Override
	public Profile findByModelName(final String modelName) {
		return profileRepository.findOne(modelName);
	}

	@Override
	public Profile saveProfile(final Profile profile) {
		saveProfileUpdate(profile);
		return profileRepository.save(profile);
	}

	private void saveProfileUpdate(final Profile profile) {
		Optional.ofNullable(profile)
				.filter(prof -> LineType.PROFILE_UPDATE.getName().equals(prof.getType()))
				.map(profileTransformer::transform)
				.ifPresent(profileUpdateService::saveProfileUpdate);
	}

}
