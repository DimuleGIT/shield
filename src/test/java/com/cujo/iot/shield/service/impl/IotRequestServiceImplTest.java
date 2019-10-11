package com.cujo.iot.shield.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hamcrest.collection.IsEmptyCollection;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.cujo.iot.shield.stream.transform.repository.IotRequestRepository;
import com.cujo.iot.shield.stream.transform.repository.model.IotRequest;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * IotRequestServiceImplTest
 */
@RunWith(MockitoJUnitRunner.class)
public class IotRequestServiceImplTest {

	private static final String REQUEST_ID = "requestId";
	private static final String DEVICE_ID = "deviceId";
	private static final String URL = "url";
	@InjectMocks
	IotRequestServiceImpl iotRequestService;

	@Mock
	private IotRequestRepository iotRequestRepository;

	@Before
	public void setUp() {
		final List<IotRequest> iotRequests = new ArrayList<>();
		final IotRequest iotRequest = IotRequest.builder().url(URL).requestId(REQUEST_ID).build();
		iotRequests.add(iotRequest);

		when(iotRequestRepository.findAll()).thenReturn(iotRequests);
		when(iotRequestRepository.save(iotRequests)).thenReturn(iotRequests);
		when(iotRequestRepository.findByDeviceId(DEVICE_ID)).thenReturn(iotRequests);
		when(iotRequestRepository.findOne(REQUEST_ID)).thenReturn(iotRequest);
	}

	@Test
	public void findAll() {
		final List<IotRequest> iotRequests = iotRequestService.findAll();
		assertThat(iotRequests, not(IsEmptyCollection.empty()));
		assertThat(iotRequests, hasSize(1));
	}

	@Test
	public void findAllEmptyList() {
		when(iotRequestRepository.findAll()).thenReturn(Collections.emptyList());
		final List<IotRequest> iotRequests = iotRequestService.findAll();
		assertThat(iotRequests, IsEmptyCollection.empty());
	}

	@Test
	public void findByRequestId() {
		final IotRequest iotRequest = iotRequestService.findByRequestId(REQUEST_ID);
		assertThat(iotRequest.getUrl(), equalTo(URL));
		assertThat(iotRequest.getRequestId(), equalTo(REQUEST_ID));
	}

	@Test
	public void findByRequestIdReturnNull() {
		when(iotRequestRepository.findOne(REQUEST_ID)).thenReturn(null);
		final IotRequest iotRequest = iotRequestService.findByRequestId(REQUEST_ID);
		assertThat(iotRequest, nullValue());
	}

	@Test
	public void findByDeviceId() {
		final List<IotRequest> iotRequests = iotRequestService.findByDeviceId(DEVICE_ID);
		assertThat(iotRequests, not(IsEmptyCollection.empty()));
		assertThat(iotRequests, hasSize(1));
	}

	@Test
	public void findByDeviceIdEmptyList() {
		when(iotRequestRepository.findByDeviceId(DEVICE_ID)).thenReturn(Collections.emptyList());
		final List<IotRequest> iotRequests = iotRequestService.findByDeviceId(DEVICE_ID);
		assertThat(iotRequests, IsEmptyCollection.empty());
	}

}