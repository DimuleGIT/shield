package com.cujo.iot.shield.service.impl;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import com.cujo.iot.shield.model.LineType;
import com.github.fge.jackson.JsonLoader;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StreamLineResolverImplTest {

	private static final String PROFILE_CREATE_JSON = "/json/profile_create.json";
	private static final String PROFILE_CREATE_BAD_JSON = "/json/xxx_create.json";
	private static final String PROFILE_UPDATE_JSON = "/json/profile_update.json";
	private static final String REQUEST_JSON = "/json/request.json";

	@InjectMocks
	StreamLineResolverImpl streamLineResolver;

	@Test
	public void getLineTypeSuccess() {
		LineType lineType = streamLineResolver.getLineType(getStreamLine(PROFILE_CREATE_JSON));
		assertThat(lineType, equalTo(LineType.PROFILE_CREATE));

		lineType = streamLineResolver.getLineType(getStreamLine(REQUEST_JSON));
		assertThat(lineType, equalTo(LineType.REQUEST));

		lineType = streamLineResolver.getLineType(getStreamLine(PROFILE_UPDATE_JSON));
		assertThat(lineType, equalTo(LineType.PROFILE_UPDATE));
	}

	@Test
	public void getLineTypeFailed() {
		LineType lineType = streamLineResolver.getLineType(getStreamLine(PROFILE_CREATE_BAD_JSON));
		assertThat(lineType, nullValue());
	}

	private String getStreamLine(final String requestJson) {
		try {
			return JsonLoader.fromResource(requestJson).toString();
		} catch (IOException e) {
			return null;
		}
	}
}