package com.ecom.ecomMongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecom.ecomMongo.model.Category;
import com.ecom.ecomMongo.model.Product;

public interface CategoryRepository extends MongoRepository<Category, Integer> {

	
}
