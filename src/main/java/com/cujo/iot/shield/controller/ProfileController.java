package com.cujo.iot.shield.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cujo.iot.shield.service.ProfileService;
import com.cujo.iot.shield.service.ProfileUpdateService;
import com.cujo.iot.shield.stream.transform.repository.model.Profile;
import com.cujo.iot.shield.stream.transform.repository.model.ProfileUpdate;

@RestController
@RequestMapping("v1/profiles")
public class ProfileController {

	private ProfileService profileService;
	private ProfileUpdateService profileUpdateService;

	@Autowired
	public ProfileController(final ProfileService profileService, final ProfileUpdateService profileUpdateService) {
		this.profileService = profileService;
		this.profileUpdateService = profileUpdateService;
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<Profile>> getAll() {
		return Optional.ofNullable(profileService.findAll())
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping(value = "/{modeName}")
	public ResponseEntity<Profile> getProfileByModelName(@PathVariable final String modeName) {
		return Optional.ofNullable(profileService.findByModelName(modeName))
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping(value = "/")
	public ResponseEntity<Profile> saveProfile(@RequestBody Profile profile) {
		return ResponseEntity.ok(profileService.saveProfile(profile));
	}

	@GetMapping(value = "update/all")
	public ResponseEntity<List<ProfileUpdate>> getAllUpdate() {
		return Optional.ofNullable(profileUpdateService.findAll())
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping(value = "/update/{modeName}")
	public ResponseEntity<List<ProfileUpdate>> getProfileUpdateByModelName(@PathVariable final String modeName) {
		return Optional.ofNullable(profileUpdateService.findByModelName(modeName))
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}


}
