package com.divatt.databaseservice.category.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.databaseservice.category.model.SubCategoryEntity;
import com.divatt.databaseservice.category.services.SubCategoryService;
import com.divatt.databaseservice.category.services.SubCategoryServiceImp;
import com.divatt.databaseservice.exception.CustomException;

@RestController
@RequestMapping("/subCategory")
public class SubCategoryController implements SubCategoryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubCategoryController.class);
	
	@Autowired
	private SubCategoryServiceImp subCategoryServiceImp;
	
	@Override
	public Page<SubCategoryEntity> getAllSubCategory(Boolean isDeleted, Pageable pagingSort) {
		try {
			LOGGER.info("Inside - SubCategoryController.getAllSubCategory");
			return subCategoryServiceImp.getAllSubCategory(isDeleted, pagingSort);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public int count() {
		try {
			LOGGER.info("Inside - SubCategoryController.count");
			return subCategoryServiceImp.count();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public Optional<SubCategoryEntity> findBySubCategoryName(String subCategoryName) {
		try {
			LOGGER.info("Inside - SubCategoryController.findBySubCategoryName");
			return subCategoryServiceImp.findBySubCategoryName(subCategoryName);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public Optional<SubCategoryEntity> getSubCategoryById(Integer id) {
		try {
			LOGGER.info("Inside - SubCategoryController.getSubCategoryById");
			return subCategoryServiceImp.getSubCategoryById(id);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	public List<SubCategoryEntity> saveAllSubcategory(List<SubCategoryEntity> subcategoryEntities) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Optional<SubCategoryEntity> saveSubCategory(SubCategoryEntity subCategoryEntity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String test() {
		return subCategoryServiceImp.test();
	}
	
}
