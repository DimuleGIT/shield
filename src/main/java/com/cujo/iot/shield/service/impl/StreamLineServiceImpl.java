package com.cujo.iot.shield.service.impl;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cujo.iot.shield.model.LineType;
import com.cujo.iot.shield.model.ResponseDTO;
import com.cujo.iot.shield.service.IotProcessService;
import com.cujo.iot.shield.service.ProfileProcessService;
import com.cujo.iot.shield.service.StreamLineResolver;
import com.cujo.iot.shield.service.StreamLineService;
import com.cujo.iot.shield.stream.transform.ResponseTransformer;

@Service
public class StreamLineServiceImpl implements StreamLineService {

	private StreamLineResolver streamLineResolver;
	private ProfileProcessService profileProcessService;
	private IotProcessService iotProcessService;
	private ResponseTransformer responseTransformer;

	@Autowired
	public StreamLineServiceImpl(final StreamLineResolver streamLineResolver, final ProfileProcessService profileProcessService,
	                             final IotProcessService iotProcessService, final ResponseTransformer responseTransformer) {
		this.streamLineResolver = streamLineResolver;
		this.profileProcessService = profileProcessService;
		this.iotProcessService = iotProcessService;
		this.responseTransformer = responseTransformer;
	}

	@Override
	public List<String> processStreamLines(final File file) {
		return streamLineResolver.getFilesLines(file)
				.map(this::processLine)
				.filter(this::isListNotEmpty)
				.flatMap(Collection::stream)
				.map(responseTransformer::serialize)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	private boolean isListNotEmpty(final List<ResponseDTO> responseDTOS) {
		return !responseDTOS.isEmpty();
	}

	private List<ResponseDTO> processLine(String streamLine) {
		final LineType lineType = streamLineResolver.getLineType(streamLine);

		switch (lineType) {
		case REQUEST:
			return iotProcessService.process(streamLine);
		case PROFILE_CREATE:
		case PROFILE_UPDATE:
			profileProcessService.process(streamLine);
			break;
		default:
			throw new UnsupportedOperationException("Not supported yet.");
		}

		return Collections.emptyList();

	}

}
