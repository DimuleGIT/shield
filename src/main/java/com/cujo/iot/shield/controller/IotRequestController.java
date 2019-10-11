package com.cujo.iot.shield.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cujo.iot.shield.service.IotRequestService;
import com.cujo.iot.shield.stream.transform.repository.model.IotRequest;

@RestController
@RequestMapping("v1/requests")
public class IotRequestController {

	private IotRequestService iotRequestService;

	@Autowired
	public IotRequestController(final IotRequestService iotRequestService) {
		this.iotRequestService = iotRequestService;
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<IotRequest>> getAll() {
		return Optional.ofNullable(iotRequestService.findAll())
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping(value = "/requests/{requestId}")
	public ResponseEntity<IotRequest> getIotRequestByRequestId(@PathVariable final String requestId) {
		return Optional.ofNullable(iotRequestService.findByRequestId(requestId))
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping(value = "/devices/{deviceId}")
	public ResponseEntity<List<IotRequest>> getIotRequestByDeviceId(@PathVariable final String deviceId) {
		return Optional.ofNullable(iotRequestService.findByDeviceId(deviceId))
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping(value = "/")
	public ResponseEntity<IotRequest> saveIotRequest(@RequestBody final IotRequest iotRequest) {
		return ResponseEntity.ok(iotRequestService.saveIotRequest(iotRequest));
	}
}
