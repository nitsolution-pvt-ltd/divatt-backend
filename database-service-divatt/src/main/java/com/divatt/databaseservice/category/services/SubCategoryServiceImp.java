package com.divatt.databaseservice.category.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.divatt.databaseservice.category.model.SubCategoryEntity;
import com.divatt.databaseservice.category.repo.SubCategoryRepo;
import com.divatt.databaseservice.exception.CustomException;

@Service
public class SubCategoryServiceImp implements SubCategoryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubCategoryServiceImp.class);
	
	@Autowired
	private SubCategoryRepo subCategoryRepo;
	
	@Override
	public int count() {
		try {
			LOGGER.info("Inside - SubCategoryServiceImp.count");
			return (int) subCategoryRepo.count();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public Optional<SubCategoryEntity> findBySubCategoryName(String subCategoryName) {
		try {
			LOGGER.info("Inside - SubCategoryServiceImp.findBySubCategoryName");
			
			return subCategoryRepo.findByCategoryName(subCategoryName);
			
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public Page<SubCategoryEntity> getAllSubCategory(Boolean isDeleted, Pageable pagingSort) {
		try {
			LOGGER.info("Inside - SubCategoryServiceImp.findBySubCategoryName");
			return subCategoryRepo.findByIsDeleted(isDeleted, pagingSort);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public Optional<SubCategoryEntity> getSubCategoryById(Integer id) {
		try {
			LOGGER.info("Inside - SubCategoryServiceImp.getSubCategoryById");
			return subCategoryRepo.findById(id);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public List<SubCategoryEntity> saveAllSubcategory(List<SubCategoryEntity> subcategoryEntities) {
		try {
			LOGGER.info("Inside - SubCategoryServiceImp.saveAllSubcategory");
			return subCategoryRepo.saveAll(subcategoryEntities);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public Optional<SubCategoryEntity> saveSubCategory(SubCategoryEntity subCategoryEntity) {
		try {
			LOGGER.info("Inside - SubCategoryServiceImp.saveSubCategory");
			SubCategoryEntity save = subCategoryRepo.save(subCategoryEntity);
			return Optional.ofNullable(save);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	public String test() {
		return "Sub category running";
	}
}
