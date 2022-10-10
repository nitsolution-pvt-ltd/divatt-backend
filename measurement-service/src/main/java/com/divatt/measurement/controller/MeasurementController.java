package com.divatt.measurement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.measurement.exception.CustomException;
import com.divatt.measurement.service.MeasurementService;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {

	
	private static final Logger LOGGER=LoggerFactory.getLogger(MeasurementController.class);
	
	@Autowired private MeasurementService measurementService;
	
	@GetMapping("/test")
	public String testAPI() {
		try {
			LOGGER.info("https://localhost:9095/dev/measurement/test");
			return measurementService.testService();
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
