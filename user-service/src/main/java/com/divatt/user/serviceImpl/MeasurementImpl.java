package com.divatt.user.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divatt.user.config.JWTConfig;
import com.divatt.user.constant.MessageConstant;
import com.divatt.user.entity.measurement.MeasurementEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repo.MeasurementRepo;
import com.divatt.user.repo.UserLoginRepo;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.MeasurementService;
import com.divatt.user.services.SequenceGenerator;

@Service
public class MeasurementImpl implements MeasurementService {

	@Autowired
	private JWTConfig jwtConfig;

	@Autowired
	private UserLoginRepo userLoginRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private MeasurementRepo measurementRepo;
	private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementImpl.class);

	@Override
	public GlobalResponse testAPIService() {
		try {
			return new GlobalResponse(MessageConstant.SUCCESS.getMessage(), "TestAPI data", 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse addMeasurementService(String token, MeasurementEntity measurementEntity) {
		try {
			Long userId = userLoginRepo.findByEmail(jwtConfig.extractUsername(token.substring(7))).get().getId();
			// LOGGER.info(userId+"");
			List<MeasurementEntity> listData = measurementRepo.findByUserIdAndGenderAndDisplyName(userId,
					measurementEntity.getGender(), measurementEntity.getDisplyName());
			if (listData.isEmpty()) {
				measurementEntity.setUserId(userId);
				measurementEntity.setId(sequenceGenerator.getNextSequence(MeasurementEntity.SEQUENCE_NAME));
				;
				// LOGGER.info(measurementEntity+"");
				measurementEntity.setCreatedOnDate(new Date());
				measurementRepo.save(measurementEntity);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.MEASUREMENT_ADDED.getMessage(), 200);
			}
			return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.MEASUREMENT_ALREADY_ADDED.getMessage(), 400);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public List<MeasurementEntity> getmeasurementList(String token, String gender) {
		try {
			if (gender.equals("all")) {
				return measurementRepo.findByUserId(
						userLoginRepo.findByEmail(jwtConfig.extractUsername(token.substring(7))).get().getId());
			} else {
				return measurementRepo.findByUserIdAndGender(
						userLoginRepo.findByEmail(jwtConfig.extractUsername(token.substring(7))).get().getId(), gender);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse updateMeasurementService(String token, String measurementId,
			MeasurementEntity updatedMeasurementEntity) {
		try {
			MeasurementEntity lastSavedMeasurement = measurementRepo.findById(Integer.parseInt(measurementId)).get();
			updatedMeasurementEntity.setId(lastSavedMeasurement.getId());
			updatedMeasurementEntity.setUserId(lastSavedMeasurement.getUserId());
			measurementRepo.save(updatedMeasurementEntity);
			return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.MEASUREMENT_UPDATED_SUCESS.getMessage(), 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse deleteMeasurement(Integer measurementId) {
		try {
			LOGGER.info("Inside - MeasurementImpl.deleteMeasurement()");
			Optional<MeasurementEntity> findMeasurementById = measurementRepo.findById(measurementId);
			if (findMeasurementById.isPresent()) {
				measurementRepo.deleteById(measurementId);
				return new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.MEASUREMENT_DELETED_SUCESSFULLY.getMessage(), 200);
			} else {
				throw new CustomException(MessageConstant.MEASUREMENT_NOT_FOUND.getMessage());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
