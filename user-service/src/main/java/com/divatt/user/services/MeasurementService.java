package com.divatt.user.services;

import java.util.List;

import com.divatt.user.entity.MeasurementEntity;
import com.divatt.user.response.GlobalResponse;

public interface MeasurementService {

	GlobalResponse testAPIService();

	GlobalResponse addMeasurementService(String token, MeasurementEntity measurementEntity);

	List<MeasurementEntity> getmeasurementList(String token, String gender);

	GlobalResponse updateMeasurementService(String token, String measurementId, MeasurementEntity measurementEntity);
	
	GlobalResponse deleteMeasurement(Integer measurementId);

}
