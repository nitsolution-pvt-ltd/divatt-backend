package com.divatt.user.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.divatt.user.entity.measurement.MeasurementEntity;
import com.divatt.user.response.GlobalResponse;

public interface MeasurementService {

	GlobalResponse testAPIService();

	GlobalResponse addMeasurementService(String token, MeasurementEntity measurementEntity);

	List<MeasurementEntity> getmeasurementList(String token);

	GlobalResponse updateMeasurementService(String token, String measurementId, MeasurementEntity measurementEntity);

}
