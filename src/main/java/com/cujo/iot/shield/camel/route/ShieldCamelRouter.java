package com.cujo.iot.shield.camel.route;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cujo.iot.shield.camel.processor.ShieldCamelProcessor;

@Component
public class ShieldCamelRouter extends RouteBuilder {

	private static final Logger log = LoggerFactory.getLogger(ShieldCamelRouter.class);
	private static final String START_CAMEL_ROUTER = "Start camel router";
	private static final String FINISH_CAMEL_ROUTER = "Finish camel router";
	private static final String CAMEL_ROUTER_TO = "file:output?fileName=output_%s.txt";
	private static final String DATE_FORMAT = "yyyyMMddHHmmss";

	@Autowired
	private ShieldCamelProcessor shieldCamelProcessor;

	@Override
	public void configure() {
		log.info(START_CAMEL_ROUTER);

		from("file:input?move=.done")
				.process(shieldCamelProcessor)
				.to(String.format(CAMEL_ROUTER_TO, getFilePrefix()));
		log.info(FINISH_CAMEL_ROUTER);
	}

	private String getFilePrefix() {
		final LocalDateTime currentDateTime = LocalDateTime.now();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		return currentDateTime.format(formatter);
	}
}
