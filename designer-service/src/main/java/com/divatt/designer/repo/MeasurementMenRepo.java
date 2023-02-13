package com.divatt.designer.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.designer.entity.MenMeasurement;

public interface MeasurementMenRepo extends MongoRepository<MenMeasurement, Integer> {
	List<MenMeasurement> findByDesignerId(Integer designerId);
}
