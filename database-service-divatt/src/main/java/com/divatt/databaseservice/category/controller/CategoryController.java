package com.divatt.databaseservice.category.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.databaseservice.category.model.CategoryEntity;
import com.divatt.databaseservice.category.services.CategoryService;
import com.divatt.databaseservice.category.services.CategoryServiceImp;
import com.divatt.databaseservice.exception.CustomException;

@RestController
@RequestMapping("/category")
public class CategoryController implements CategoryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryServiceImp categoryServiceImp;
	
	@Override
	@GetMapping("/list")
	public Page<CategoryEntity> getAllCategory(Boolean isDeleted, Pageable pagingSort) {
		try {
			LOGGER.info("Inside - CategoryController.getAllCategory");
			return categoryServiceImp.getAllCategory(isDeleted, pagingSort);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	@GetMapping("/view/{catId}")
	public Optional<CategoryEntity> getCategoryById(@PathVariable() Integer id) {
		try {
			LOGGER.info("Inside - CategoryController.getCategoryById");
			return categoryServiceImp.getCategoryById(id);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	@PostMapping("/saveAll")
	public List<CategoryEntity> saveAllCategory(@RequestBody List<CategoryEntity> categoryEntities) {
		try {
			return categoryServiceImp.saveAllCategory(categoryEntities);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	@PostMapping("/save")
	public Optional<CategoryEntity> saveCategory(@RequestBody CategoryEntity categoryEntity) {
		try {
			return categoryServiceImp.saveCategory(categoryEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	@GetMapping("/count")
	public int count() {
		try {
			return categoryServiceImp.count();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@Override
	@GetMapping("/findByCategoryName")
	public Optional<CategoryEntity> findByCategoryName(@PathVariable String categoryName) {
		try {
			return categoryServiceImp.findByCategoryName(categoryName);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
}
