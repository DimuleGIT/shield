package com.cujo.iot.shield.service;

import java.io.File;
import java.util.List;

public interface StreamLineService {
	List<String> processStreamLines(File file);
}
