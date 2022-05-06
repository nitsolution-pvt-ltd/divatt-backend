package com.divatt.admin.contoller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.category.CategoryEntity;
import com.divatt.admin.entity.category.SubCategoryEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.CategoryRepo;
import com.divatt.admin.services.CategoryService;




@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	 
	
	@PostMapping("/add")
	public GlobalResponse postCategoryDetails(@Valid @RequestBody CategoryEntity categoryEntity) {
		LOGGER.info("Inside - CategoryController.postCategoryDetails()");

		try {
			 return this.categoryService.postCategoryDetails(categoryEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		} 

	}
		
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getCategoryDetails(			
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, 
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, 			
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - CategoryController.getListCategoryDetails()");

		try {		
			return this.categoryService.getCategoryDetails(page, limit, sort, sortName, isDeleted, keyword,
					sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@RequestMapping(value = { "/getAllCategory" }, method = RequestMethod.GET)
	public List<SubCategoryEntity> getAllCategoryDetails(			
			@RequestParam(defaultValue = "DESC") String sort, 
			@RequestParam(defaultValue = "true") Boolean Status,
			@RequestParam(defaultValue = "false") Boolean isDeleted) {
		LOGGER.info("Inside - CategoryController.getAllCategoryDetails()");

		try {		
			return this.categoryService.getAllCategoryDetails(isDeleted, Status);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@GetMapping("/view/{catId}")
	public Optional<CategoryEntity> viewCategoryDetails(@PathVariable() Integer catId) {
		LOGGER.info("Inside - SubCategoryController.viewCategoryDetails()");
		try {
			return this.categoryService.viewCategoryDetails(catId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@PutMapping("/update/{catId}")
	public GlobalResponse putCategoryDetails(@Valid @RequestBody CategoryEntity categoryEntity,
			@PathVariable("catId") Integer catId) {
		LOGGER.info("Inside - CategoryController.putCategoryDetails()");
		try {
			return this.categoryService.putCategoryDetailsService(categoryEntity, catId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@PutMapping("/delete")
	public GlobalResponse putCategoryDelete(@RequestBody() CategoryEntity categoryEntity) {
		LOGGER.info("Inside - CategoryController.putCategoryDelete()");
		try {
			if (categoryEntity.getId()!=null){
				return this.categoryService.putCategoryDeleteService(categoryEntity.getId());
			}else {
				throw new CustomException("Sub Category Not Found!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}	
	}
	
	@PutMapping("/status")
	public GlobalResponse putCategoryStatus(@RequestBody() CategoryEntity categoryEntity) {
		LOGGER.info("Inside - CategoryController.putCategoryStatus()");
		try {
			if (categoryEntity.getId()!=null){
				return this.categoryService.putCategoryStatusService(categoryEntity.getId());
			}else {
				throw new CustomException("Category Not Found!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}	
	}
	
	@PutMapping("/muldelete")
	public GlobalResponse putCategoryMulDelete(@RequestBody() List<Integer> CateID) {
		LOGGER.info("Inside - CategoryController.putCategoryMulDelete()");
		try {
			if (!CateID.equals(null)){
				return this.categoryService.putCategoryMulDeleteService(CateID);
			}else {
				throw new CustomException("Category Id Not Found!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}	
	}
	
	@PostMapping("/categoryVerification")
	public List<Integer> categoryVerification(@RequestBody List<Integer> categoryId)
	{
		try
		{
			return this.categoryService.categoryVerification(categoryId);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
