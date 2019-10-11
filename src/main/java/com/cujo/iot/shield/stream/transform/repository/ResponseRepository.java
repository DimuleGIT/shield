package com.cujo.iot.shield.stream.transform.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cujo.iot.shield.stream.transform.repository.model.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
	Response findByRequestId(String requestId);
	List<Response> findByDeviceId(String deviceId);
}
