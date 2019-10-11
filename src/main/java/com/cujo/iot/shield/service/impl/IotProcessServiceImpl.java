package com.cujo.iot.shield.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.cujo.iot.shield.model.Action;
import com.cujo.iot.shield.model.RequestDTO;
import com.cujo.iot.shield.model.ResponseDTO;
import com.cujo.iot.shield.service.IotProcessService;
import com.cujo.iot.shield.service.IotRequestService;
import com.cujo.iot.shield.service.ProfileService;
import com.cujo.iot.shield.service.ResponseService;
import com.cujo.iot.shield.stream.transform.IotRequestTransformer;
import com.cujo.iot.shield.stream.transform.ResponseTransformer;
import com.cujo.iot.shield.stream.transform.repository.model.IotRequest;
import com.cujo.iot.shield.stream.transform.repository.model.Profile;
import com.cujo.iot.shield.util.JsonUtils;

@Service
public class IotProcessServiceImpl implements IotProcessService {

	private static final Logger log = LoggerFactory.getLogger(IotProcessServiceImpl.class);
	private static final String DESERIALIZE_REQUEST_DTO = "Can't process deserializeRequestDTO {}";
	private IotRequestService iotRequestService;
	private ProfileService profileService;
	private ResponseService responseService;
	private IotRequestTransformer iotRequestTransformer;
	private ResponseTransformer responseTransformer;

	@Value("${quarantine.timestamp}")
	private int quarantineTimestamp;

	public IotProcessServiceImpl(final IotRequestService iotRequestService,
	                             final ProfileService profileService,
	                             final ResponseService responseService, final IotRequestTransformer iotRequestTransformer,
	                             final ResponseTransformer responseTransformer) {
		this.iotRequestService = iotRequestService;
		this.profileService = profileService;
		this.responseService = responseService;
		this.iotRequestTransformer = iotRequestTransformer;
		this.responseTransformer = responseTransformer;
	}

	@Override
	public List<ResponseDTO> process(final String streamLine) {
		return Optional.ofNullable(getRequestDTO(streamLine))
				.map(this::saveAndGetResponse)
				.orElseGet(Collections::emptyList);
	}

	private List<ResponseDTO> saveAndGetResponse(final RequestDTO requestDTO) {
		saveIotRequest(requestDTO);
		return getResponseDTOS(requestDTO);
	}

	private RequestDTO getRequestDTO(final String streamLine) {
		return Optional.ofNullable(streamLine)
				.map(this::deserializeRequestDTO)
				.orElse(null);
	}

	private void saveIotRequest(final RequestDTO requestDTO) {
		final IotRequest iotRequest = iotRequestTransformer.transform(requestDTO);
		iotRequestService.saveIotRequest(iotRequest);
	}

	private List<ResponseDTO> getResponseDTOS(final RequestDTO requestDTO) {
		List<ResponseDTO> responseDTOS = new ArrayList<>();

		Optional.ofNullable(getResponseDTO(requestDTO))
				.ifPresent(responseDTOS::add);

		Optional.ofNullable(getDeviceQuarantineAction(requestDTO))
				.ifPresent(responseDTOS::add);

		responseDTOS.stream()
	         .map(responseTransformer::transform)
		     .forEach(responseService::saveResponse);

		return responseDTOS;
	}

	private RequestDTO deserializeRequestDTO(final String streamLine) {
		try {
			return JsonUtils.deserialize(streamLine, RequestDTO.class);
		} catch (IOException e) {
			log.trace(DESERIALIZE_REQUEST_DTO, e.getMessage());
			return null;
		}
	}

	private ResponseDTO getResponseDTO(final RequestDTO requestDTO) {
		return Optional.ofNullable(requestDTO)
				.map(RequestDTO::getModelName)
				.map(profileService::findByModelName)
				.map(profile -> transformToResponseDTO(requestDTO, profile))
				.orElse(null);
	}

	private ResponseDTO transformToResponseDTO(final RequestDTO requestDTO,
	                                           final Profile profile) {
		return ResponseDTO.builder()
				.action(getResponseAction(requestDTO, profile))
				.requestId(requestDTO.getRequestId())
				.build();
	}

	private ResponseDTO getDeviceQuarantineAction(final RequestDTO requestDTO) {
		if (isDeviceNeedMovedToQuarantine(requestDTO)) {
			return ResponseDTO.builder()
					.action(Action.QUARANTINE.getName())
					.deviceId(requestDTO.getDeviceId())
					.build();
		}
		return null;
	}

	private boolean isDeviceNeedMovedToQuarantine(final RequestDTO requestDTO) {
		return Optional.ofNullable(requestDTO)
				.filter(this::isDevicePolicyBlock)
				.map(RequestDTO::getDeviceId)
				.map(iotRequestService::findByDeviceId)
				.map(Collection::stream)
				.orElseGet(Stream::empty)
				.filter(request -> isDifferentRequestId(requestDTO, request))
				.anyMatch(iotRequest -> isTimestampValid(iotRequest, requestDTO));
	}

	private boolean isDifferentRequestId(final RequestDTO requestDTO, final IotRequest request) {
		return  Optional.ofNullable(requestDTO)
					.map(RequestDTO::getRequestId)
					.filter(requestId -> requestId.equals(request.getRequestId()))
					.isPresent();
	}

	private boolean isTimestampValid(final IotRequest iotRequest,
	                                 final RequestDTO requestDTO) {
		return requestDTO.getTimestamp().compareTo(iotRequest.getTimestamp() + quarantineTimestamp) < 0;
	}

	private boolean isUrlWhitelisted(RequestDTO requestDTO, Profile profile) {
		return Action.BLOCK.getName().equals(profile.getDefaultPolicy()) &&
				requestDTO.getUrl().equals(profile.getWhitelist());
	}

	private boolean isDevicePolicyBlock(RequestDTO requestDTO) {
		return Optional.ofNullable(profileService.findByModelName(requestDTO.getModelName()))
				.map(Profile::getDefaultPolicy)
				.filter(defaultPolicy -> Action.BLOCK.getName().equalsIgnoreCase(defaultPolicy))
				.isPresent();
	}

	private boolean isUrlBlacklisted(RequestDTO requestDTO, Profile profile) {
		return Action.ALLOW.getName().equals(profile.getDefaultPolicy()) &&
				requestDTO.getUrl().equals(profile.getBlacklist());
	}

	private String getResponseAction(RequestDTO requestDTO, Profile profile) {
		return (isUrlWhitelisted(requestDTO, profile)
				|| isUrlBlacklisted(requestDTO, profile)) ? Action.ALLOW.getName() : Action.BLOCK.getName();
	}
}
