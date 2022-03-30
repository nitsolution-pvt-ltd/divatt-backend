package com.divatt.databaseservice.category.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.divatt.databaseservice.category.model.SubCategoryEntity;

public interface SubCategoryService {
	
	int count();
	
	Page<SubCategoryEntity> getAllSubCategory(Boolean isDeleted, Pageable pagingSort);
	
	Optional<SubCategoryEntity> getSubCategoryById(Integer id);
	
	Optional<SubCategoryEntity> saveSubCategory(SubCategoryEntity subCategoryEntity);
	
	List<SubCategoryEntity> saveAllSubcategory(List<SubCategoryEntity> subcategoryEntities);
	
	Optional<SubCategoryEntity> findBySubCategoryName(String subCategoryName);
	
	static boolean check() throws ClassNotFoundException {
		Class<?> forName = Class.forName("com.divatt.databaseservice.category.controller.SubCategoryController");
		int length = forName.getDeclaredMethods().length;
		Class<?>[] interfaces = forName.getInterfaces();
		Class<?> class1 = interfaces[0];
		int length2 = class1.getMethods().length;
		
		if (length == length2)
			return true;
		
		return false;
	}
}
