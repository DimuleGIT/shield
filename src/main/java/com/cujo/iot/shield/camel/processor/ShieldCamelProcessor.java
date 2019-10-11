package com.cujo.iot.shield.camel.processor;

import java.io.File;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cujo.iot.shield.service.StreamLineService;

@Component
public class ShieldCamelProcessor implements Processor {

	private static final Logger log = LoggerFactory.getLogger(ShieldCamelProcessor.class);
	private static final String START_CAMEL_ROUTER = "Start camel processor";
	private static final String FINISH_CAMEL_ROUTER = "Finish camel processor";

	private StreamLineService streamLineService;

	@Autowired
	public ShieldCamelProcessor(final StreamLineService streamLineService) {
		this.streamLineService = streamLineService;
	}

	@Override
	public void process(Exchange exchange) {
		log.info(START_CAMEL_ROUTER);
		final File file = exchange.getIn().getBody(File.class);
		final List<String> outputFileLines = streamLineService.processStreamLines(file);
		exchange.getIn().setBody(String.join("\n", outputFileLines));
		log.info(FINISH_CAMEL_ROUTER);
	}
}
