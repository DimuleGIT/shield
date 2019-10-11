package com.cujo.iot.shield.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cujo.iot.shield.service.ResponseService;
import com.cujo.iot.shield.stream.transform.repository.ResponseRepository;
import com.cujo.iot.shield.stream.transform.repository.model.Response;

@Service
public class ResponseServiceImpl implements ResponseService {
	
	private ResponseRepository responseRepository;

	@Autowired
	public ResponseServiceImpl(final ResponseRepository responseRepository) {
		this.responseRepository = responseRepository;
	}

	@Override
	public List<Response> findAll() {
		return responseRepository.findAll();
	}

	@Override
	public Response findByRequestId(final String requestId) {
		return responseRepository.findByRequestId(requestId);
	}

	@Override
	public List<Response> findByDeviceId(final String deviceId) {
		return responseRepository.findByDeviceId(deviceId);
	}

	@Override
	public Response saveResponse(final Response response) {
		return responseRepository.save(response);
	}

}
