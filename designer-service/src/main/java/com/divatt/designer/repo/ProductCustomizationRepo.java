package com.divatt.designer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.designer.entity.product.ProductCustomizationEntity;

public interface ProductCustomizationRepo extends MongoRepository<ProductCustomizationEntity, Integer>{

}
