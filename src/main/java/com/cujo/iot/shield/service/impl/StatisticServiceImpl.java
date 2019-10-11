package com.cujo.iot.shield.service.impl;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cujo.iot.shield.model.Action;
import com.cujo.iot.shield.model.StatisticDTO;
import com.cujo.iot.shield.service.IotRequestService;
import com.cujo.iot.shield.service.ProfileService;
import com.cujo.iot.shield.service.ProfileUpdateService;
import com.cujo.iot.shield.service.ResponseService;
import com.cujo.iot.shield.service.StatisticService;
import com.cujo.iot.shield.stream.transform.repository.model.IotRequest;
import com.cujo.iot.shield.stream.transform.repository.model.ProfileUpdate;
import com.cujo.iot.shield.stream.transform.repository.model.Response;
import static com.cujo.iot.shield.model.StatisticsType.BLOCK_ISSUED;
import static com.cujo.iot.shield.model.StatisticsType.BLOCK_MISSED;
import static com.cujo.iot.shield.model.StatisticsType.PROTECTED_DEVICES;
import static com.cujo.iot.shield.model.StatisticsType.SUSPECTED_DEVICES;

@Service
public class StatisticServiceImpl implements StatisticService {

	private IotRequestService iotRequestService;
	private ProfileUpdateService profileUpdateService;
	private ResponseService responseService;

	@Autowired
	public StatisticServiceImpl(final IotRequestService iotRequestService,
	                            final ProfileUpdateService profileUpdateService,
	                            final ResponseService responseService) {
		this.iotRequestService = iotRequestService;
		this.profileUpdateService = profileUpdateService;
		this.responseService = responseService;
	}

	@Override
	public List<StatisticDTO> getAll() {
		return Arrays.asList(getProtectedDevices(),
		                     getSuspectedDevices(),
				             getBlockMissedDueDelayedProfileUpdates(),
		                     getBlockIssuedDueDelayedProfileUpdates());
	}

	private StatisticDTO getProtectedDevices() {

		final long protectedDeviceCount =
				responseService.findAll().stream()
						.filter(this::isDeviceBlocked)
						.distinct()
						.count();

		return StatisticDTO.builder()
				.type(PROTECTED_DEVICES.name())
				.value((int)protectedDeviceCount)
				.build();
	}

	private StatisticDTO getSuspectedDevices() {
		final long suspectedDeviceCount =
				responseService.findAll().stream()
				  .filter(this::isDeviceInQuarantine)
				  .count();

		return StatisticDTO.builder()
				.type(SUSPECTED_DEVICES.name())
				.value((int)suspectedDeviceCount)
				.build();
	}

	private boolean isDeviceInQuarantine(final Response response) {
		return Action.QUARANTINE.getName().equals(response.getAction());
	}

	private boolean isDeviceBlocked(final Response response) {
		return Action.BLOCK.getName().equals(response.getAction());
	}

	private boolean isDeviceAllowed(final Response response) {
		return Action.ALLOW.getName().equals(response.getAction());
	}

	private StatisticDTO getBlockMissedDueDelayedProfileUpdates() {

		final long missedDueDelayedCount =
				responseService.findAll().stream()
						.filter(this::isDeviceAllowed)
						.map(Response::getRequestId)
						.map(iotRequestService::findByRequestId)
						.filter(this::isProfileUpdateDelay)
						.count();

		return StatisticDTO.builder()
				.type(BLOCK_MISSED.name())
				.value((int)missedDueDelayedCount)
				.build();
	}

	private boolean isProfileUpdateDelay(final IotRequest iotRequest) {
		return profileUpdateService.findByModelName(iotRequest.getModelName())
				.stream()
				.filter(profileUpdate -> iotRequest.getUrl().equals(profileUpdate.getBlacklist()))
				.map(ProfileUpdate::getTimestamp)
				.anyMatch(profileUpdateTimestamp -> profileUpdateTimestamp.compareTo(iotRequest.getTimestamp()) > 0);
	}

	private StatisticDTO getBlockIssuedDueDelayedProfileUpdates() {

		final long issuedDueDelayedCount =
				responseService.findAll().stream()
						.filter(this::isDeviceBlocked)
						.map(Response::getRequestId)
						.map(iotRequestService::findByRequestId)
						.filter(this::isProfileUpdateDelay)
						.count();

		return StatisticDTO.builder()
				.type(BLOCK_ISSUED.name())
				.value((int)issuedDueDelayedCount)
				.build();
	}

}
