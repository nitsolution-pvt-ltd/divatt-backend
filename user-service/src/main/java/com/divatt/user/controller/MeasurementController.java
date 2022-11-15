package com.divatt.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.user.entity.measurement.MeasurementEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.MeasurementService;

@RestController
@RequestMapping("/userMeasurement")
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
	
	@PostMapping("/addMeasurement")
	public GlobalResponse addMeasurement(@RequestHeader("Authorization") String token,@RequestBody MeasurementEntity measurementEntity){
		try {
			return this.measurementService.addMeasurementService(token,measurementEntity);
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/getMeasurementlist/{gender}")
	public List<MeasurementEntity> getMeasurementList(@RequestHeader("Authorization") String token, @PathVariable String gender){
		try {
			return this.measurementService.getmeasurementList(token, gender);
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@PutMapping("/updateMeasurement")
	public GlobalResponse updateMeasurement(@RequestHeader("Authorization") String token,
			@RequestParam String measurementId,@RequestBody MeasurementEntity measurementEntity) {
		try {
			return this.measurementService.updateMeasurementService(token,measurementId,measurementEntity);
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
