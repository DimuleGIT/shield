package com.cujo.iot.shield.service;

import java.util.List;
import com.cujo.iot.shield.stream.transform.repository.model.IotRequest;

public interface IotRequestService {
    List<IotRequest> findAll();
    IotRequest findByRequestId(String requestId);
    IotRequest saveIotRequest(IotRequest iotRequest);
    List<IotRequest> findByDeviceId(String deviceId);
    List<IotRequest> findByModelName(String modelName);
}
