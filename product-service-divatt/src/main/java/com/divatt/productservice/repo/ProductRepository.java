package com.divatt.productservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.productservice.entity.ProductMasterEntity;
import com.google.common.base.Optional;

public interface ProductRepository extends MongoRepository<ProductMasterEntity, Integer>{

	Optional<ProductMasterEntity> findByProductName(String productName);
	

}
