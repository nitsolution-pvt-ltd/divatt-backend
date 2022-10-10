package com.divatt.user.serviceImpl;

import org.springframework.stereotype.Service;

import com.divatt.user.exception.CustomException;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.MeasurementService;


@Service
public class MeasurementImpl implements MeasurementService{

	@Override
	public GlobalResponse testAPIService() {
		try {
			return new GlobalResponse("Success", "TestAPI data", 200);
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
