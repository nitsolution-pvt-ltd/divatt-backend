package com.divatt.designer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.designer.entity.product.ProductMasterEntity_2;

@Repository
public interface ProductRepo_2 extends MongoRepository<ProductMasterEntity_2, Integer> {

}
