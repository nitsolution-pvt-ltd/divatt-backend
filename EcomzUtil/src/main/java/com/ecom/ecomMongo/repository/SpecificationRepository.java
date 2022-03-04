package com.ecom.ecomMongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecom.ecomMongo.model.Specification;

public interface SpecificationRepository extends MongoRepository<Specification, Integer> {

	
}
