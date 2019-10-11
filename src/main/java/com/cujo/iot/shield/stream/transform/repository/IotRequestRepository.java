package com.cujo.iot.shield.stream.transform.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cujo.iot.shield.stream.transform.repository.model.IotRequest;

@Repository
public interface IotRequestRepository extends JpaRepository<IotRequest, String> {
	List<IotRequest> findByDeviceId(String deviceId);
	List<IotRequest> findByModelName(String modelName);
}
