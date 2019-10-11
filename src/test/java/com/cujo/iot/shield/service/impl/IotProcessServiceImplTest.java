package com.cujo.iot.shield.service.impl;

import java.io.IOException;
import java.util.List;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.cujo.iot.shield.model.RequestDTO;
import com.cujo.iot.shield.model.ResponseDTO;
import com.cujo.iot.shield.service.IotRequestService;
import com.cujo.iot.shield.service.ProfileService;
import com.cujo.iot.shield.service.ResponseService;
import com.cujo.iot.shield.stream.transform.IotRequestTransformer;
import com.cujo.iot.shield.stream.transform.ResponseTransformer;
import com.cujo.iot.shield.stream.transform.repository.model.IotRequest;
import com.cujo.iot.shield.stream.transform.repository.model.Profile;
import com.cujo.iot.shield.stream.transform.repository.model.Response;
import com.github.fge.jackson.JsonLoader;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.not;
/**
 * IotProcessServiceImplTest
 */
@RunWith(MockitoJUnitRunner.class)
public class IotProcessServiceImplTest {

	private static final String REQUEST_JSON = "/json/request.json";
	private static final String BAD_REQUEST_JSON = "/json/bad_request.json";
	private static final String IPHONE = "Iphone";

	@InjectMocks
	IotProcessServiceImpl iotProcessService;

	@Mock
	private IotRequestService iotRequestService;

	@Mock
	private ProfileService profileService;

	@Mock
	private IotRequestTransformer iotRequestTransformer;

	@Mock
	private IotRequest iotRequest;

	@Mock
	private ResponseTransformer responseTransformer;

	@Mock
	private Profile profile;

	@Mock
	private Response response;

	@Mock
	ResponseService responseService;

	@Before
	public void setUp() {
		when(iotRequestTransformer.transform(any(RequestDTO.class))).thenReturn(iotRequest);
		when(iotRequestService.saveIotRequest(iotRequest)).thenReturn(iotRequest);
		when(responseTransformer.transform(any(ResponseDTO.class))).thenReturn(response);
		when(responseService.saveResponse(response)).thenReturn(response);
		when(profileService.findByModelName(any(String.class))).thenReturn(profile);
		when(profile.getModelName()).thenReturn(IPHONE);
	}

	@Test
	public void processSuccess() {
		final List<ResponseDTO> responseDTOs = iotProcessService.process(getStreamLine(REQUEST_JSON));
		assertThat(responseDTOs, not(IsEmptyCollection.empty()));
		verify(iotRequestTransformer).transform(any(RequestDTO.class));
		verify(iotRequestService).saveIotRequest(eq(iotRequest));
	}

	@Test
	public void processBadRequest() {
		final List<ResponseDTO> responseDTOs = iotProcessService.process(getStreamLine(BAD_REQUEST_JSON));
		assertThat(responseDTOs, IsEmptyCollection.empty());
		verify(iotRequestTransformer, never()).transform(any());
		verify(iotRequestService, never()).saveIotRequest(any());
	}

	private String getStreamLine(final String requestJson) {
		try {
			return JsonLoader.fromResource(requestJson).toString();
		} catch (IOException e) {
			return null;
		}
	}
}