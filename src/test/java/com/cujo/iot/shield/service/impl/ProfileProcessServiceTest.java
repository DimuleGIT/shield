package com.cujo.iot.shield.service.impl;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.cujo.iot.shield.model.ProfileDTO;
import com.cujo.iot.shield.service.ProfileService;
import com.cujo.iot.shield.stream.transform.ProfileTransformer;
import com.cujo.iot.shield.stream.transform.repository.model.Profile;
import com.github.fge.jackson.JsonLoader;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileProcessServiceTest {

	private static final String PROFILE_JSON = "/json/profile_create.json";
	private static final String BAD_PROFILE_JSON = "/json/bad_profile_create.json";

	@InjectMocks
	private ProfileProcessServiceImpl profileProcessService;

	@Mock
	private ProfileTransformer profileTransformer;

	@Mock
	private ProfileService profileService;

	@Mock
	private ProfileDTO profileDTO;

	@Mock
	private Profile profile;

	@Before
	public void setUp() {
		when(profileTransformer.transform(any(ProfileDTO.class))).thenReturn(profile);
	}

	@Test
	public void processSuccess() {
		profileProcessService.process(getStreamLine(PROFILE_JSON));
		verify(profileTransformer).transform(any(ProfileDTO.class));
		verify(profileService).saveProfile(profile);
	}

	@Test
	public void processFailed() {
		profileProcessService.process(getStreamLine(BAD_PROFILE_JSON));
		verify(profileTransformer, never()).transform(any(ProfileDTO.class));
		verify(profileService, never()).saveProfile(profile);
	}

	private String getStreamLine(final String requestJson) {
		try {
			return JsonLoader.fromResource(requestJson).toString();
		} catch (IOException e) {
			return null;
		}
	}
}