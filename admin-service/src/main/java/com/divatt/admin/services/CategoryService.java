package com.divatt.admin.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.divatt.admin.entity.CategoryEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.UserCategoryResponse;
import com.divatt.admin.entity.UserResponseEntity;

public interface CategoryService {

	GlobalResponse postCategoryDetails(@Valid CategoryEntity categoryEntity);

	Map<String, Object> getCategoryDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy);

	List<CategoryEntity> getAllCategoryDetails(Boolean isDeleted, Boolean status);

	ResponseEntity<?> getCategoryListDetailsService(Boolean isDeleted, Boolean status);

	Optional<CategoryEntity> viewCategoryDetails(Integer catId);

	GlobalResponse putCategoryDetailsService(@Valid CategoryEntity categoryEntity, Integer catId);

	GlobalResponse putCategoryDeleteService(Integer id);

	GlobalResponse putCategoryStatusService(Integer id);

	GlobalResponse putCategoryMulDeleteService(List<Integer> cateID);

	List<Integer> categoryVerification(List<Integer> categoryId);

	List<UserCategoryResponse> viewByCategoryNameService();

	UserResponseEntity viewByCategoryName(String categoryName, String subCategoryName);

	List<CategoryEntity> getAllCategoryDetails();

}
