package com.cujo.iot.shield.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cujo.iot.shield.service.ProfileUpdateService;
import com.cujo.iot.shield.stream.transform.repository.ProfileUpdateRepository;
import com.cujo.iot.shield.stream.transform.repository.model.ProfileUpdate;

@Service
public class ProfileUpdateServiceImpl implements ProfileUpdateService {
	private ProfileUpdateRepository profileUpdateRepository;

	@Autowired
	public ProfileUpdateServiceImpl(final ProfileUpdateRepository profileUpdateRepository) {
		this.profileUpdateRepository = profileUpdateRepository;
	}

	@Override
	public List<ProfileUpdate> findAll() {
		return profileUpdateRepository.findAll();
	}

	@Override
	public List<ProfileUpdate> findByModelName(final String modelName) {
		return profileUpdateRepository.findByModelName(modelName);
	}

	@Override
	public ProfileUpdate saveProfileUpdate(final ProfileUpdate profileUpdate) {
		return profileUpdateRepository.save(profileUpdate);
	}

}
