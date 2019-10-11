package com.cujo.iot.shield.service;

import java.io.File;
import java.util.stream.Stream;
import com.cujo.iot.shield.model.LineType;

public interface StreamLineResolver {
    LineType getLineType(String streamLine);
    Stream<String> getFilesLines(final File file)
;}
