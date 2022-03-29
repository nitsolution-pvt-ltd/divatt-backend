package com.divatt.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.productservice.entity.ProductEntity;

@Repository
public interface ProductRepo extends MongoRepository<ProductEntity, Integer>{

}
