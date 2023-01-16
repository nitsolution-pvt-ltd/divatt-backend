package com.divatt.user.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.MeasurementEntity;

public interface MeasurementRepo extends MongoRepository<MeasurementEntity, Integer>{

	List<MeasurementEntity> findByUserId(Long id);

	List<MeasurementEntity> findByUserIdAndGenderAndDisplyName(Long userId, String gender, String displyName);
	
	List<MeasurementEntity> findByUserIdAndGender(Long userId, String gender);

}
