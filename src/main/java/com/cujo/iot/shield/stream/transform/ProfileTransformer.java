package com.cujo.iot.shield.stream.transform;

import com.cujo.iot.shield.model.ProfileDTO;
import com.cujo.iot.shield.stream.transform.repository.model.Profile;
import com.cujo.iot.shield.stream.transform.repository.model.ProfileUpdate;

public interface ProfileTransformer {
	Profile transform(ProfileDTO profileDTO);
	ProfileUpdate transform(Profile profile);
}
