package com.cujo.iot.shield.service;

import java.util.List;
import com.cujo.iot.shield.stream.transform.repository.model.Response;

public interface ResponseService {
	List<Response> findAll();
	Response findByRequestId(String requestId);
	List<Response> findByDeviceId(String deviceId);
	Response saveResponse(Response response);
}
