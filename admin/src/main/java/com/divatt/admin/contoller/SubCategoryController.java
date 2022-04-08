package com.divatt.admin.contoller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.divatt.admin.entity.category.SubCategoryEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.SubCategoryRepo;
import com.divatt.admin.services.SubCategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/subcategory")
public class SubCategoryController {
	
	@Autowired
	private SubCategoryService subCategoryService;
	
	@Autowired
	SubCategoryRepo subCategoryRepo;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubCategoryController.class); 
	
	@PostMapping("/add")
	public GlobalResponse postSubCategoryDetails(@Valid @RequestBody SubCategoryEntity subCategoryEntity) {
		LOGGER.info("Inside - SubCategoryController.postSubCategoryDetails()");

		try {
			 return this.subCategoryService.postSubCategoryDetails(subCategoryEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
		
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getSubCategoryDetails(			
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, 
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, 			
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - SubCategoryController.getSubCategoryDetails()");

		try {		
			return this.subCategoryService.getSubCategoryDetails(page, limit, sort, sortName, isDeleted, keyword,
					sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@GetMapping("/view/{catId}")
	public Optional<SubCategoryEntity> viewSubCategoryDetails(@PathVariable() Integer catId) {
		LOGGER.info("Inside - SubCategoryController.viewSubCategoryDetails()");
		try {
			return this.subCategoryService.viewSubCategoryDetails(catId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@PutMapping("/update/{catId}")
	public GlobalResponse putSubCategoryDetails(@Valid @RequestBody SubCategoryEntity subCategoryEntity,
			@PathVariable("catId") Integer catId) {
		LOGGER.info("Inside - SubCategoryController.putSubCategoryDetails()");
		try {
			return this.subCategoryService.putSubCategoryDetailsService(subCategoryEntity, catId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@PutMapping("/delete")
	public GlobalResponse putSubCategoryDelete(@RequestBody() SubCategoryEntity subCategoryEntity) {
		LOGGER.info("Inside - SubCategoryController.putSubCategoryDelete()");
		try {
			if (subCategoryEntity.getId()!=null){
				return this.subCategoryService.putSubCategoryDeleteService(subCategoryEntity.getId());
			}else {
				throw new CustomException("Sub Category Not Found!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}	
	}
	
	@PutMapping("/status")
	public GlobalResponse putSubCategoryStatus(@RequestBody() SubCategoryEntity subCategoryEntity) {
		LOGGER.info("Inside - SubCategoryController.putSubCategoryStatus()");
		try {
			if (subCategoryEntity.getId()!=null){
				return this.subCategoryService.putSubCategoryStatusService(subCategoryEntity.getId());
			}else {
				throw new CustomException("SubCategory Not Found!");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}	
	}

}
