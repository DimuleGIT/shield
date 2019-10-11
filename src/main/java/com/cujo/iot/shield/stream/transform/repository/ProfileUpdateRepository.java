package com.cujo.iot.shield.stream.transform.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cujo.iot.shield.stream.transform.repository.model.ProfileUpdate;

@Repository
public interface ProfileUpdateRepository extends JpaRepository<ProfileUpdate, String> {
	List<ProfileUpdate> findByModelName(String modelName);
}
