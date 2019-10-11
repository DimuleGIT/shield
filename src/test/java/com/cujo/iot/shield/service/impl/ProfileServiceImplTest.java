package com.cujo.iot.shield.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.cujo.iot.shield.stream.transform.repository.ProfileRepository;
import com.cujo.iot.shield.stream.transform.repository.model.Profile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * ProfileServiceImplTest
 */
@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceImplTest {

	private static final String MODEL_NAME = "MODEL_NAME";
	private static final String WHITELIST = "WHITELIST";
	@InjectMocks
	ProfileServiceImpl profileService;

	@Mock
	private ProfileRepository profileRepository;

	@Before
	public void setUp() {
		final List<Profile> profiles = new ArrayList<>();
		final Profile profile = Profile.builder().modelName(MODEL_NAME).whitelist(WHITELIST).build();
		profiles.add(profile);

		when(profileRepository.findAll()).thenReturn(profiles);
		when(profileRepository.save(profiles)).thenReturn(profiles);
		when(profileRepository.findOne(MODEL_NAME)).thenReturn(profile);
	}

	@Test
	public void findAll() {
		final List<Profile> profiles = profileService.findAll();
		assertThat(profiles, not(IsEmptyCollection.empty()));
		assertThat(profiles, hasSize(1));
	}

	@Test
	public void findAllEmptyList() {
		when(profileRepository.findAll()).thenReturn(Collections.emptyList());
		final List<Profile> profiles = profileService.findAll();
		assertThat(profiles, IsEmptyCollection.empty());
	}

	@Test
	public void findByModelName() {
		final Profile profile = profileService.findByModelName(MODEL_NAME);
		assertThat(profile.getWhitelist(), equalTo(WHITELIST));
		assertThat(profile.getModelName(), equalTo(MODEL_NAME));
	}

	@Test
	public void findByRequestIdReturnNull() {
		when(profileRepository.findOne(MODEL_NAME)).thenReturn(null);
		final Profile profile = profileService.findByModelName(MODEL_NAME);
		assertThat(profile, nullValue());
	}

}