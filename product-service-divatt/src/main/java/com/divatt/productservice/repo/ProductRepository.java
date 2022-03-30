package com.divatt.productservice.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.productservice.entity.ProductMasterEntity;

public interface ProductRepository extends MongoRepository<ProductMasterEntity, Integer>{

	Optional<ProductMasterEntity> findByProductName(String productName);
	

}
