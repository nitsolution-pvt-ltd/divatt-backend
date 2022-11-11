package com.divatt.user.serviceImpl;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.divatt.user.config.JWTConfig;
import com.divatt.user.entity.measurement.MeasurementEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repo.MeasurementRepo;
import com.divatt.user.repo.UserLoginRepo;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.MeasurementService;
import com.divatt.user.services.SequenceGenerator;


@Service
public class MeasurementImpl implements MeasurementService{
	
	@Autowired private JWTConfig jwtConfig;
	
	@Autowired private UserLoginRepo userLoginRepo;
	
	@Autowired private SequenceGenerator sequenceGenerator;
	
	@Autowired private MeasurementRepo measurementRepo;
	private static final Logger LOGGER=LoggerFactory.getLogger(MeasurementImpl.class);

	@Override
	public GlobalResponse testAPIService() {
		try {
			return new GlobalResponse("Success", "TestAPI data", 200);
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse addMeasurementService(String token, MeasurementEntity measurementEntity) {
		try {
			Long userId=userLoginRepo.findByEmail(jwtConfig.extractUsername(token.substring(7))).get().getId();
			LOGGER.info(userId+"");
			measurementEntity.setUserId(userId);
			measurementEntity.setId(sequenceGenerator.getNextSequence(MeasurementEntity.SEQUENCE_NAME));;
			LOGGER.info(measurementEntity+"");
			measurementEntity.setCreatedOnDate(new Date());
			measurementRepo.save(measurementEntity);
			return new GlobalResponse("Success", "Measurement added successfully", 200);
			}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public List<MeasurementEntity> getmeasurementList(String token) {
		try {
			return measurementRepo.findByUserId(userLoginRepo.findByEmail(jwtConfig.extractUsername(token.substring(7))).get().getId());
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse updateMeasurementService(String token, String measurementId,MeasurementEntity updatedMeasurementEntity) {
		try {
			MeasurementEntity lastSavedMeasurement=measurementRepo.findById(Integer.parseInt(measurementId)).get();
			updatedMeasurementEntity.setId(lastSavedMeasurement.getId());
			measurementRepo.save(updatedMeasurementEntity);
			return new GlobalResponse("Success", "Measurement updated successfully", 200);
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
