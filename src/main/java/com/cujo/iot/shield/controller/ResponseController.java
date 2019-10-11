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
import com.cujo.iot.shield.service.ResponseService;
import com.cujo.iot.shield.stream.transform.repository.model.Response;

@RestController
@RequestMapping("v1/responses")
public class ResponseController {

	private ResponseService responseService;

	@Autowired
	public ResponseController(final ResponseService responseService) {
		this.responseService = responseService;
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<Response>> getAll() {
		return ResponseEntity.ok(responseService.findAll());
	}

	@GetMapping(value = "/{requestId}")
	public ResponseEntity<Response> getResponseByRequestId(@PathVariable String requestId) {
		return Optional.ofNullable(responseService.findByRequestId(requestId))
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());

	}

	@GetMapping(value = "/{deviceId}")
	public ResponseEntity<List<Response>> getResponseByDeviceId(@PathVariable String deviceId) {
		return Optional.ofNullable(responseService.findByDeviceId(deviceId))
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());

	}

	@PostMapping(value = "/")
	public ResponseEntity<Response> saveResponse(@RequestBody Response response) {
		return ResponseEntity.ok(responseService.saveResponse(response));
	}
}
