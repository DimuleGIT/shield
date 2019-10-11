package com.cujo.iot.shield.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import com.cujo.iot.shield.model.LineType;
import com.cujo.iot.shield.service.StreamLineResolver;
import com.jayway.jsonpath.JsonPath;

@Service
public class StreamLineResolverImpl implements StreamLineResolver {

	private static final String TYPE = "$.type";

	@Override
	public LineType getLineType(final String streamLine) {
		return Optional.ofNullable(streamLine)
				.map(stream -> JsonPath.read(stream, TYPE))
				.map(String.class::cast)
				.map(LineType::fromValue)
				.orElse(null);
	}

	@Override
	public Stream<String> getFilesLines(final File file) {
		try {
			return Files.lines(file.toPath());
		} catch (Exception e) {
			return Stream.empty();
		}
	}
}
