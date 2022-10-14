package com.divatt.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.user.exception.CustomException;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.MeasurementService;
import com.netflix.discovery.converters.Auto;

@RestController
public class MeasurementController {

	@Autowired private MeasurementService measurementService;
	
	
	@GetMapping("/test")
	public GlobalResponse testAPI() {
		try {
			return this.measurementService.testAPIService();
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
}
