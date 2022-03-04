package com.ecom.ecomMongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecom.ecomMongo.model.SubCategory;

public interface SubCategoryRepository extends MongoRepository<SubCategory, Integer>  {

	
}
