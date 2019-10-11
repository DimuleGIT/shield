package com.cujo.iot.shield.service;

import java.util.List;
import com.cujo.iot.shield.stream.transform.repository.model.ProfileUpdate;

public interface ProfileUpdateService {
	List<ProfileUpdate> findAll();
	List<ProfileUpdate> findByModelName(String modelName);
	ProfileUpdate saveProfileUpdate(ProfileUpdate profileUpdate);
}
