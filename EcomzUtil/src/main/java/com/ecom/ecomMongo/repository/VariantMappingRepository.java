/**
 * 
 */
package com.ecom.ecomMongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecom.ecomMongo.model.VariantMappings;

/**
 * @author situpand
 *
 */
public interface VariantMappingRepository extends MongoRepository<VariantMappings,Integer> {

}
