package com.divatt.measurement.serviceImpl;

import com.divatt.measurement.exception.CustomException;
import com.divatt.measurement.service.MeasurementService;

public class MeasurementServiceImpl implements MeasurementService{

	@Override
	public String testService() {
		try {
			return "Ok";
		}catch(Exception e){
			throw new CustomException(e.getMessage());
		}
	}

}
