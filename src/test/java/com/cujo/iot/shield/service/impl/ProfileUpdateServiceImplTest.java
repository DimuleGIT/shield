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
import com.cujo.iot.shield.stream.transform.repository.ProfileUpdateRepository;
import com.cujo.iot.shield.stream.transform.repository.model.ProfileUpdate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * ProfileUpdateServiceImplTest
 */
@RunWith(MockitoJUnitRunner.class)
public class ProfileUpdateServiceImplTest {

	private static final String MODEL_NAME = "MODEL_NAME";
	private static final String WHITELIST = "WHITELIST";
	@InjectMocks
	ProfileUpdateServiceImpl profileUpdateService;

	@Mock
	private ProfileUpdateRepository profileUpdateRepository;

	@Before
	public void setUp() {
		final List<ProfileUpdate> profiles = new ArrayList<>();
		final ProfileUpdate profileUpdate = ProfileUpdate.builder().modelName(MODEL_NAME).whitelist(WHITELIST).build();
		profiles.add(profileUpdate);

		when(profileUpdateRepository.findAll()).thenReturn(profiles);
		when(profileUpdateRepository.save(profiles)).thenReturn(profiles);
		when(profileUpdateRepository.findOne(MODEL_NAME)).thenReturn(profileUpdate);
		when(profileUpdateRepository.findByModelName(MODEL_NAME)).thenReturn(profiles);
	}

	@Test
	public void findAll() {
		final List<ProfileUpdate> profiles = profileUpdateService.findAll();
		assertThat(profiles, not(IsEmptyCollection.empty()));
		assertThat(profiles, hasSize(1));
	}

	@Test
	public void findAllEmptyList() {
		when(profileUpdateRepository.findAll()).thenReturn(Collections.emptyList());
		final List<ProfileUpdate> profiles = profileUpdateService.findAll();
		assertThat(profiles, IsEmptyCollection.empty());
	}

	@Test
	public void findByModelName() {
		final List<ProfileUpdate> profileUpdate = profileUpdateService.findByModelName(MODEL_NAME);
		assertThat(profileUpdate.get(0), notNullValue());
		assertThat(profileUpdate.get(0).getWhitelist(), equalTo(WHITELIST));
		assertThat(profileUpdate.get(0).getModelName(), equalTo(MODEL_NAME));
	}


}