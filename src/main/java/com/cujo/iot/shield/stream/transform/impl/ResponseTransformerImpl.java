package com.cujo.iot.shield.stream.transform.impl;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.cujo.iot.shield.model.ResponseDTO;
import com.cujo.iot.shield.stream.transform.ResponseTransformer;
import com.cujo.iot.shield.stream.transform.repository.model.Response;
import com.cujo.iot.shield.util.JsonUtils;

@Service
public class ResponseTransformerImpl implements ResponseTransformer {

	private static final Logger log = LoggerFactory.getLogger(ResponseTransformerImpl.class);
	private static final String SERIALIZATION_ERROR = "Error when trying serialize responseDTO {}";

	@Override
	public String serialize(final ResponseDTO responseDTO) {
		try {
			return JsonUtils.serialize(responseDTO);
		} catch (IOException e) {
			log.trace(SERIALIZATION_ERROR, e.getMessage());
			return null;
		}
	}

	@Override
	public Response transform(final ResponseDTO responseDTO) {
		return Response.builder()
				.action(responseDTO.getAction())
				.deviceId(responseDTO.getDeviceId())
				.requestId(responseDTO.getRequestId())
				.build();
	}
}
