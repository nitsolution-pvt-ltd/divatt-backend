package com.ecom.ecomMomgo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.ecomMongo.model.Category;
import com.ecom.ecomMongo.repository.CategoryRepository;

@Service
public class CategoryServices {

	@Autowired
	private CategoryRepository repository;

	public List<Category> saveCategoryService(List<Category> catagories) {
		
		return repository.saveAll(catagories);
	}

	public Optional<Category> getCategoryById(Integer catId) {
		// TODO Auto-generated method stub
		return repository.findById(catId);
	}

	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
}
