package com.divatt.designer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.designer.entity.MeasurementEntity;

public interface MeasurementRepo extends MongoRepository<MeasurementEntity, Long> {

}
