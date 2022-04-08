package com.divatt.admin.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.category.CategoryEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.CategoryRepo;




@Service
public class CategoryService {
	
private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	SequenceGenerator sequenceGenerator;
	
	
	public GlobalResponse postCategoryDetails(@RequestBody CategoryEntity categoryEntity) {
		LOGGER.info("Inside - CategoryService.postCategoryDetails()");
		
		try {
			
			Optional<CategoryEntity> findByCategoryName = categoryRepo
					.findByCategoryName(categoryEntity.getCategoryName());
			if (findByCategoryName.isPresent()) {
				return new GlobalResponse("ERROR", "Category Already Exists!", 200);
			} else {
				CategoryEntity filterCatDetails = new CategoryEntity();

				
				filterCatDetails.setId(sequenceGenerator.getNextSequence(CategoryEntity.SEQUENCE_NAME));
				filterCatDetails.setCategoryName(categoryEntity.getCategoryName());
				filterCatDetails.setCategoryDescrition(categoryEntity.getCategoryDescrition());
				filterCatDetails.setCategoryImage(categoryEntity.getCategoryImage());
				filterCatDetails.setCreatedBy(categoryEntity.getCreatedBy());
				filterCatDetails.setCreatedOn(new Date());
				filterCatDetails.setLevel(categoryEntity.getLevel());
				filterCatDetails.setParentId("0");
				filterCatDetails.setIsActive(true);
				filterCatDetails.setIsDeleted(false);				
			
				categoryRepo.save(filterCatDetails);				
				return new GlobalResponse("SUCCESS", "Category Added Succesfully", 200);				
			}
			
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}


	public Map<String, Object> getCategoryDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			int CountData = (int) categoryRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}
			
			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<CategoryEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = categoryRepo.findByIsDeletedAndParentId(isDeleted,"0",pagingSort);
			} else {				
				findAll = categoryRepo.Search(keyword, isDeleted,"0", pagingSort);

			}
			

			int totalPage = findAll.getTotalPages() - 1;
			if (totalPage < 0) {
				totalPage = 0;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", findAll.getContent());
			response.put("currentPage", findAll.getNumber());
			response.put("total", findAll.getTotalElements());
			response.put("totalPage", totalPage);
			response.put("perPage", findAll.getSize());
			response.put("perPageElement", findAll.getNumberOfElements());

			if (findAll.getSize() <= 1) {
				throw new CustomException("Institute Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	public Optional<CategoryEntity> viewCategoryDetails(Integer catId) {
		try {
			Optional<CategoryEntity> findById = this.categoryRepo.findById(catId);
			if (!(findById.isPresent())) {
				throw new CustomException("Category Not Found!");
			} else {
				return findById;
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	public GlobalResponse putCategoryDetailsService(CategoryEntity categoryEntity, Integer catId) {
			
		try {						
			Optional<CategoryEntity> findByCategoryRow = categoryRepo.findById(catId);
			
			if (!findByCategoryRow.isPresent()) {
				return new GlobalResponse("ERROR", "Category Not Found!", 200);
			} else {				
				CategoryEntity filterCatDetails = findByCategoryRow.get();

				filterCatDetails.setCategoryName(categoryEntity.getCategoryName());
				filterCatDetails.setCategoryDescrition(categoryEntity.getCategoryDescrition());
				filterCatDetails.setCategoryImage(categoryEntity.getCategoryImage());
				filterCatDetails.setCreatedBy(categoryEntity.getCreatedBy());
				filterCatDetails.setCreatedOn(new Date());
				filterCatDetails.setLevel(categoryEntity.getLevel());
				filterCatDetails.setParentId("0");
				filterCatDetails.setIsActive(true);
				filterCatDetails.setIsDeleted(false);							
				categoryRepo.save(filterCatDetails);
				
				return new GlobalResponse("SUCCESS", "Category Updated Succesfully", 200);				
			}
			
		} catch (Exception e) {			
			throw new CustomException(e.getMessage());
		}

	}
	
	
	public GlobalResponse putCategoryDeleteService(Integer CatId) {
		try {
			Optional<CategoryEntity> findById = categoryRepo.findById(CatId);
			CategoryEntity filterCatDetails = findById.get();
			if (!findById.isPresent()) {
				return new GlobalResponse("ERROR", "Category Not Found!", 200);
			} else {				
				filterCatDetails.setIsDeleted(true);
				filterCatDetails.setCreatedOn(new Date());
				categoryRepo.save(filterCatDetails);				

				return new GlobalResponse("SUCCESS", "Category Deleted Successfully", 200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	


	public GlobalResponse putCategoryStatusService(Integer CatId) {
		try {
			
			Optional<CategoryEntity> findById = categoryRepo.findById(CatId);
			CategoryEntity filterCatDetails = findById.get();
			
			if (filterCatDetails.getId() == null) {
				return new GlobalResponse("ERROR", "Category Not Found!", 200);
				
			} else {
				Boolean isStatus = null;
				if (filterCatDetails.getIsActive() == false) {
					isStatus = true;
				} else {
					isStatus = false;
				}			

				filterCatDetails.setIsActive(isStatus);
				filterCatDetails.setCreatedOn(new Date());
				categoryRepo.save(filterCatDetails);
				
				return new GlobalResponse("SUCCESS", "Status Changed Successfully", 200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	
}