package com.ecom.ecomMomgo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.ecomMongo.model.Specification;
import com.ecom.ecomMongo.repository.SpecificationRepository;

@Service
public class SpecificationService {

	@Autowired
	private SpecificationRepository repository;
	
	public List<Specification> saveSpecificationService(List<Specification> specs)
	{
		
		return repository.saveAll(specs);
	}
	
	public Optional<Specification> getSpecificationById(Integer specid)
	{
		return repository.findById(specid);
	}
	
	public List<Specification> getAllSpecifications()
	{
		return repository.findAll();
	}
}
