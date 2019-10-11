package com.cujo.iot.shield.service;

import java.util.List;
import com.cujo.iot.shield.model.StatisticDTO;

public interface StatisticService {
	List<StatisticDTO> getAll();
}
