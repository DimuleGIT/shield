package com.cujo.iot.shield.stream.transform;

import com.cujo.iot.shield.model.ResponseDTO;
import com.cujo.iot.shield.stream.transform.repository.model.Response;

public interface ResponseTransformer {
	String serialize(ResponseDTO responseDTO);
	Response transform(ResponseDTO responseDTO);
}
