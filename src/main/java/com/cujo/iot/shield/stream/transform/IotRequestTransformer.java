package com.cujo.iot.shield.stream.transform;

import com.cujo.iot.shield.model.RequestDTO;
import com.cujo.iot.shield.stream.transform.repository.model.IotRequest;

public interface IotRequestTransformer {
	IotRequest transform(RequestDTO requestDTO);
}
