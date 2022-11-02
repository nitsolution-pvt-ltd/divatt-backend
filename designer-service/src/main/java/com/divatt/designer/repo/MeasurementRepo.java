package com.divatt.designer.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.designer.entity.Measurement;
@Repository
public interface MeasurementRepo extends MongoRepository<Measurement, Integer> {
	List<Measurement> findByDesignerId(Integer designerId);
}
