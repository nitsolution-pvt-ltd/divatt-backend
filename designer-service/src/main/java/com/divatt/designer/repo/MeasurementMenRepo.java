package com.divatt.designer.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.divatt.designer.entity.MenMeasurement;

public interface MeasurementMenRepo extends MongoRepository<MenMeasurement, Integer> {
	Optional<MenMeasurement> findByDesignerId(Integer designerId);
}
