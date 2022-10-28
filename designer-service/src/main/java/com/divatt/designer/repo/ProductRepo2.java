package com.divatt.designer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.designer.entity.product.ProductMasterEntity2;

@Repository
public interface ProductRepo2 extends MongoRepository<ProductMasterEntity2, Integer> {

}
