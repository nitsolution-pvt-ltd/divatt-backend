package com.divatt.category.service;

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
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;


import com.divatt.category.Exception.CustomException;
import com.divatt.category.Entity.SubCategoryEntity;
import com.divatt.category.Repository.SubCategoryRepo;
import com.divatt.category.response.GlobalResponse;


@Service
public class SubCategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubCategoryService.class);
	
	@Autowired
	SubCategoryRepo subCategoryRepo;
	
//	@Autowired
//	private FieldValidation fieldValidation;
	
	    @Autowired private MongoOperations mongo;

	    public Object getNextSequence(String seqName)
	    {
	    	SubCategoryEntity  counter = mongo.findAndModify(
	            query(where("_id").is(seqName)),
	            new Update().inc("seq",1),
	            options().returnNew(true).upsert(true),
	            SubCategoryEntity.class);
	        return counter.getId();
	    }
	
	
	public GlobalResponse postSubCategoryDetails(@RequestBody SubCategoryEntity subCategoryEntity) {
		LOGGER.info("Inside - SubCategoryService.postSubCategoryDetails()");
		
		try {
			
			Optional<SubCategoryEntity> findBySubCategoryName = subCategoryRepo.findByCategoryName(subCategoryEntity.getCategoryName());
			if (findBySubCategoryName.isPresent()) {
				throw new CustomException("Sub Category Already Exists!");
			} else {
				SubCategoryEntity filterSubCatDetails = new SubCategoryEntity();

				filterSubCatDetails.setCategoryName(subCategoryEntity.getCategoryName());
				filterSubCatDetails.setCategoryDescrition(subCategoryEntity.getCategoryDescrition());
				filterSubCatDetails.setCategoryImage(subCategoryEntity.getCategoryImage());
				filterSubCatDetails.setCreatedBy(subCategoryEntity.getCreatedBy());
				filterSubCatDetails.setCreatedOn(new Date());
				filterSubCatDetails.setLevel(subCategoryEntity.getLevel());
				filterSubCatDetails.setParentId(subCategoryEntity.getParentId());
				filterSubCatDetails.setIsActive(true);
				filterSubCatDetails.setIsDeleted(false);				
			
				subCategoryRepo.save(filterSubCatDetails);				
				return new GlobalResponse("SUCCESS", "Sub Category Added Succesfully", 200);
				
			}
			
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}


	public Map<String, Object> getSubCategoryDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			Optional<String> keyword, Optional<String> sortBy) {
		try {
			int CountData = (int) subCategoryRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}
			
			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<SubCategoryEntity> findAll = null;

			if (keyword.get().isEmpty()) {
				findAll = subCategoryRepo.findByIsDeleted(isDeleted,pagingSort);
//				findAll = subCategoryRepo.findAll(pagingSort);
				LOGGER.info("Inside - SubCategoryController.getSubCategoryDetails()ss"+findAll+"//"+limit);

			} else {
//				findAll = subCategoryRepo.Search(keyword.get(), isDeleted, pagingSort);

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


	public Optional<SubCategoryEntity> viewSubCategoryDetails(String catId) {
		try {
			Optional<SubCategoryEntity> findById = this.subCategoryRepo.findById(catId);
			if (!(findById.isPresent())) {
				throw new CustomException("Category Not Found!");
			} else {
				return findById;
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	public GlobalResponse putSubCategoryDetailsService(SubCategoryEntity subCategoryEntity, String catId) {
			
		try {						
			Optional<SubCategoryEntity> findByCategoryRow = subCategoryRepo.findById(catId);
			
			if (!findByCategoryRow.isPresent()) {
				throw new CustomException("Sub Category Not Exists!");
			} else {				
				SubCategoryEntity filterSubCatDetails = findByCategoryRow.get();

				filterSubCatDetails.setCategoryName(subCategoryEntity.getCategoryName());
				filterSubCatDetails.setCategoryDescrition(subCategoryEntity.getCategoryDescrition());
				filterSubCatDetails.setCategoryImage(subCategoryEntity.getCategoryImage());
				filterSubCatDetails.setCreatedBy(subCategoryEntity.getCreatedBy());
				filterSubCatDetails.setCreatedOn(new Date());
				filterSubCatDetails.setLevel(subCategoryEntity.getLevel());
				filterSubCatDetails.setParentId(subCategoryEntity.getParentId());
				filterSubCatDetails.setIsActive(true);
				filterSubCatDetails.setIsDeleted(false);							
				subCategoryRepo.save(filterSubCatDetails);
				
				return new GlobalResponse("SUCCESS", "Sub Category Updated Succesfully", 200);				
			}
			
		} catch (Exception e) {			
			throw new CustomException(e.getMessage());
		}

	}
	
	


	public GlobalResponse putSubCategoryDeleteService(Object id) {
		try {
			Optional<SubCategoryEntity> findById = subCategoryRepo.findById((String) id);
			SubCategoryEntity filterSubCatDetails = findById.get();
			if (!findById.isPresent()) {
				throw new CustomException("Sub Category Not Exists!");
			} else {				
				filterSubCatDetails.setIsDeleted(true);
				filterSubCatDetails.setCreatedOn(new Date());
				subCategoryRepo.save(filterSubCatDetails);				

				return new GlobalResponse("SUCCESS", "Sub Category Deleted Successfully", 200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	public GlobalResponse putSubCategoryStatusService(Object id) {
		try {
			
			Optional<SubCategoryEntity> findById = subCategoryRepo.findById((String) id);
			SubCategoryEntity filterSubCatDetails = findById.get();
			
			if (filterSubCatDetails.getId() == null) {
				throw new CustomException("Sub Category Not Found!");
			} else {
				Boolean isStatus = null;
				if (filterSubCatDetails.getIsActive() == false) {
					isStatus = true;
				} else {
					isStatus = false;
				}			

				filterSubCatDetails.setIsActive(isStatus);
				filterSubCatDetails.setCreatedOn(new Date());
				subCategoryRepo.save(filterSubCatDetails);
				
				return new GlobalResponse("SUCCESS", "Status Changed Successfully", 200);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
