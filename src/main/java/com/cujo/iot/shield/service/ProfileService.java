package com.cujo.iot.shield.service;

import java.util.List;
import com.cujo.iot.shield.stream.transform.repository.model.Profile;

public interface ProfileService {
	List<Profile> findAll();
	Profile findByModelName(String modelName);
	Profile saveProfile(Profile profile);
}
