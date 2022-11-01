package com.divatt.designer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.divatt.designer.entity.MenMeasurement;

public interface MeasurementMenRepo extends MongoRepository<MenMeasurement, Integer> {

}
