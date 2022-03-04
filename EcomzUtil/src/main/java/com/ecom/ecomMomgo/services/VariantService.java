/**
 * 
 */
package com.ecom.ecomMomgo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.ecomMongo.model.Variant;
import com.ecom.ecomMongo.repository.VariantsRepository;

/**
 * @author situpand
 *
 */
@Service
public class VariantService {

	@Autowired
	private VariantsRepository repository;
	
	/**
	 * @param variants
	 * saving variants to mongoDB
	 * @return 
	 */
	public List<Variant> saveVariantService(List<Variant> variants) {
		// TODO Auto-generated method stub
		repository.deleteAll();
		return repository.saveAll(variants);
		
	}

	/**
	 * @param varId
	 * @return
	 */
	public Optional<Variant> getVariantById(Integer varId) {
		// TODO Auto-generated method stub
		return repository.findById(varId);
	}

	/**
	 * @return
	 */
	public List<Variant> getAllVariant() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	
	
	

	
}
