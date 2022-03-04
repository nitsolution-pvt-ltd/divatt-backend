/**
 * 
 */
package com.ecom.ecomMongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecom.ecomMongo.model.Variant;

/**
 * @author situpand
 *
 */
public interface VariantsRepository extends MongoRepository<Variant, Integer> {

}
