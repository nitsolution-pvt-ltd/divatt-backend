package com.divatt.databaseservice.category.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.divatt.databaseservice.category.model.CategoryEntity;
import com.divatt.databaseservice.category.repo.CategoryRepo;
import com.divatt.databaseservice.exception.CustomException;

@Service
public class CategoryServiceImp implements CategoryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImp.class);
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public Page<CategoryEntity> getAllCategory(Boolean isDeleted, Pageable pagingSort) {
		try {
			LOGGER.info("Inside - CategoryServiceImp.getAllCategory");
			return categoryRepo.findByIsDeleted(isDeleted, pagingSort);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public Optional<CategoryEntity> getCategoryById(Integer id) {
		try {
			LOGGER.info("Inside - CategoryServiceImp.getCategoryById");
			return categoryRepo.findById(id);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public List<CategoryEntity> saveAllCategory(List<CategoryEntity> categoryEntities) {
		try {
			LOGGER.info("Inside - CategoryServiceImp.saveAllCategory");
			return categoryRepo.saveAll(categoryEntities);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public Optional<CategoryEntity> saveCategory(CategoryEntity categoryEntity) {
		try {
			LOGGER.info("Inside - CategoryServiceImp.saveCategory");
			CategoryEntity save = categoryRepo.save(categoryEntity);
			return Optional.ofNullable(save);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public int count() {
		try {
			LOGGER.info("Inside - CategoryServiceImp.count");
			return (int) categoryRepo.count();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public Optional<CategoryEntity> findByCategoryName(String categoryName) {
		try {
			LOGGER.info("Inside - CategoryServiceImp.findByCategoryName");
			return categoryRepo.findByCategoryName(categoryName);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
