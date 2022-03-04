/**
 * 
 */
package com.ecom.ecomMomgo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.ecomMongo.model.VariantMappings;
import com.ecom.ecomMongo.repository.VariantMappingRepository;

/**
 * @author situpand
 *
 */
@Service
public class VariantMappingService {

	@Autowired
	VariantMappingRepository repository;
	/**
	 * @param mappings
	 */
	public List<VariantMappings> addVariantMappings(List<VariantMappings> mappings) {
		return repository.saveAll(mappings);

	}
	
	public Optional<VariantMappings> getVariantMappingById(Integer id) {
		return repository.findById(id);
	}

	public List<VariantMappings> getAllVariantMapping() {
		return repository.findAll();
	}

}
