package com.cujo.iot.shield.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cujo.iot.shield.model.StatisticDTO;
import com.cujo.iot.shield.service.StatisticService;

@RestController
@RequestMapping("v1/statistics")
public class StatisticController {

	private StatisticService statisticService;

	@Autowired
	public StatisticController(final StatisticService statisticService) {
		this.statisticService = statisticService;
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<StatisticDTO>> getAll() {
		return ResponseEntity.ok(statisticService.getAll());
	}


}
