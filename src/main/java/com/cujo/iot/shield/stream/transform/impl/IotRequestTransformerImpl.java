package com.cujo.iot.shield.stream.transform.impl;

import org.springframework.stereotype.Service;
import com.cujo.iot.shield.model.RequestDTO;
import com.cujo.iot.shield.stream.transform.IotRequestTransformer;
import com.cujo.iot.shield.stream.transform.repository.model.IotRequest;

@Service
public class IotRequestTransformerImpl implements IotRequestTransformer {

	@Override
	public IotRequest transform(final RequestDTO requestDTO) {
		return IotRequest.builder()
				.deviceId(requestDTO.getDeviceId())
				.modelName(requestDTO.getModelName())
				.requestId(requestDTO.getRequestId())
				.timestamp(requestDTO.getTimestamp())
				.url(requestDTO.getUrl())
				.build();
	}
}
