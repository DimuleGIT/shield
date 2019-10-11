package com.cujo.iot.shield.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cujo.iot.shield.service.IotRequestService;
import com.cujo.iot.shield.stream.transform.repository.IotRequestRepository;
import com.cujo.iot.shield.stream.transform.repository.model.IotRequest;

@Service
public class IotRequestServiceImpl implements IotRequestService {

	private IotRequestRepository iotRequestRepository;

	@Autowired
	public IotRequestServiceImpl(final IotRequestRepository iotRequestRepository) {
		this.iotRequestRepository = iotRequestRepository;
	}

	@Override
	public List<IotRequest> findAll() {
		return iotRequestRepository.findAll();
	}

	@Override
	public IotRequest saveIotRequest(final IotRequest iotRequest) {
		return iotRequestRepository.save(iotRequest);
	}

	@Override
	public List<IotRequest> findByDeviceId(final String deviceId) {
		return iotRequestRepository.findByDeviceId(deviceId);
	}

	@Override
	public List<IotRequest> findByModelName(final String modelName) {
		return iotRequestRepository.findByModelName(modelName);
	}

	@Override
	public IotRequest findByRequestId(final String requestId) {
		return iotRequestRepository.findOne(requestId);
	}
}
