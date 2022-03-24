package com.divatt.databaseservice.category.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.divatt.databaseservice.category.model.CategoryEntity;

public interface CategoryService {

	Page<CategoryEntity> getAllCategory(Boolean isDeleted, Pageable pagingSort);

	Optional<CategoryEntity> getCategoryById(Integer id);

	Optional<CategoryEntity> saveCategory(CategoryEntity categoryEntity);

	List<CategoryEntity> saveAllCategory(List<CategoryEntity> categoryEntities);
	
	int count();
	
	Optional<CategoryEntity> findByCategoryName(String categoryName);
	
	static boolean check() throws ClassNotFoundException {
		Class<?> forName = Class.forName("com.divatt.databaseservice.category.controller.CategoryController");
		int length = forName.getDeclaredMethods().length;
		Class<?>[] interfaces = forName.getInterfaces();
		Class<?> class1 = interfaces[0];
		int length2 = class1.getMethods().length;
		
		if (length == length2) {
			return true;
		} else {
			return false;
		}
	}
}
