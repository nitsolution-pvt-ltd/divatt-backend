package com.ecom.ecomMongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecom.ecomMongo.model.Product;

/**
 * @author aryansh
 *
 */
public interface ProductRepository extends MongoRepository<Product, Integer>{
	
	
	

}
