package com.divatt.admin.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.ProductMeasurementEntity;
@Repository
public interface MeasurementRepo extends MongoRepository<ProductMeasurementEntity, Integer>{

	boolean findBySubCategoryName(String subCategoryName);

}
