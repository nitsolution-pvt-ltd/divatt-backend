package com.divatt.admin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.category.CategoryEntity;
import com.divatt.admin.entity.specification.SpecificationEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.CategoryRepo;
import com.divatt.admin.repo.SpecificationRepo;

@Service
public class SpecificationService {

	@Autowired
	private SpecificationRepo specRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	public GlobalResponse addSpecification( @Valid SpecificationEntity specificationEntity) {
		try
		{
				specificationEntity.setId(sequenceGenerator.getNextSequence(SpecificationEntity.SEQUENCE_NAME));
				specificationEntity.setIsActive(true);
				specificationEntity.setIsDeleted(false);
				specificationEntity.setAddonDate(new Date());
				specRepo.save(specificationEntity);
				return new GlobalResponse("Success!!", "Data saved Successfully", 200);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}


	public List<SpecificationEntity> listOfSpecification(String categoryName) {
		try
		{
			//String categoryName=categoryRepo.findById(categoryId).get().getCategoryName();
			if(categoryName.toLowerCase().contains("women"))
			{
				categoryName="women";
			}
			else if(categoryName.toLowerCase().contains("men"))
			{
				System.out.println("Hiii");
				categoryName="men";
			}
			else if(categoryName.toLowerCase().contains("kid"))
			{
				categoryName="kid";
			}
			else
			{
				throw new CustomException("Invalid category name");
			}
			Query query= new Query();
			query.addCriteria(Criteria.where("categoryName").is(categoryName).and("isActive").is(true));
			List<SpecificationEntity> listOfSpecificationData=mongoOperations.find(query, SpecificationEntity.class);
			Query query1= new Query();
			query1.addCriteria(Criteria.where("categoryName").is("all").and("isActive").is(true));
			List<SpecificationEntity>allListOfSpecification=mongoOperations.find(query1, SpecificationEntity.class);
			List<SpecificationEntity> allSpeList=new ArrayList<SpecificationEntity>();
			allSpeList.addAll(allListOfSpecification);
			allSpeList.addAll(listOfSpecificationData);
			return allSpeList;
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}


	public GlobalResponse updateSpec(SpecificationEntity specificationData, Integer specId) {
		try
		{
			if(specRepo.existsById(specId))
			{
				specificationData.setId(specId);
				specificationData.setAddonDate(new Date());
				specRepo.save(specificationData);
				return new GlobalResponse("Success!!", "Specification Updated", 200);
			}
			else
			{
				throw new CustomException("Product not found");
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse deleteSpec(Integer specId) {
		try {
			if(specRepo.existsById(specId))
			{
				SpecificationEntity specificationEntity=specRepo.findById(specId).get();
				specificationEntity.setIsActive(false);
				specificationEntity.setIsDeleted(true);
				specRepo.save(specificationEntity);
				return new GlobalResponse("Success", "Specification deleted successfully", 200);
			}
			else
			{
				throw new CustomException("Product Not Found");
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}


	public Map<String, Object> getAllSpec(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			int CountData = (int) specRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<SpecificationEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = specRepo.findByIsDeleted(isDeleted, pagingSort);
			} else {
				findAll = specRepo.Search(keyword, isDeleted, pagingSort);

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
				throw new CustomException("Product not found!");
			} else {
				return response;
			}
		}
		 catch(Exception e) {
			 throw new CustomException(e.getMessage());
		 }
	}
}
