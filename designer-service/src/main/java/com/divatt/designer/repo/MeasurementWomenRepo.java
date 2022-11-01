package com.divatt.designer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.designer.entity.WomenMeasurement;
@Repository
public interface MeasurementWomenRepo extends MongoRepository<WomenMeasurement, Integer> {

}
