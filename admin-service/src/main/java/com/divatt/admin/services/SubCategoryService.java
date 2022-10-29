package com.divatt.admin.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.category.SubCategoryEntity;

public interface SubCategoryService {

	GlobalResponse postSubCategoryDetails(@Valid SubCategoryEntity subCategoryEntity);

	Map<String, Object> getSubCategoryDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy);

	List<SubCategoryEntity> getAllSubCategoryDetails(String catId, Boolean isDeleted, Boolean status);

	Optional<SubCategoryEntity> viewSubCategoryDetails(Integer catId);

	GlobalResponse putSubCategoryDetailsService(@Valid SubCategoryEntity subCategoryEntity, Integer catId);

	GlobalResponse putSubCategoryDeleteService(Integer id);

	GlobalResponse putSubCategoryStatusService(Integer id);

	GlobalResponse putSubCategoryMulDeleteService(List<Integer> cateID);

	List<Integer> subcategoryVerification(List<Integer> subCategoryId);

}
