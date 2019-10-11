package com.cujo.iot.shield.stream.transform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cujo.iot.shield.stream.transform.repository.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
}
