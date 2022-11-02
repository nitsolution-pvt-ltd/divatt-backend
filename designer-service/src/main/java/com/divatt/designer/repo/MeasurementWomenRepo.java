package com.divatt.designer.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.designer.entity.WomenMeasurement;
@Repository
public interface MeasurementWomenRepo extends MongoRepository<WomenMeasurement, Integer> {
	Optional<WomenMeasurement> findByDesignerId(Integer designerId);
}
