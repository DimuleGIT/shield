package com.cujo.iot.shield.service;

import java.util.List;
import com.cujo.iot.shield.model.ResponseDTO;

public interface IotProcessService {
	List<ResponseDTO> process(String streamLine);
}
