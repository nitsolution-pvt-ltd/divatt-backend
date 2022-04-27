package com.divatt.admin.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.divatt.admin.repo.SpecificationRepo;

@Service
public class SpecificationService {

	@Autowired
	private SpecificationRepo specRepo;
	
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
			query.addCriteria(Criteria.where("categoryName").is(categoryName));
			List<SpecificationEntity> listOfSpecificationData=mongoOperations.find(query, SpecificationEntity.class);
			Query query1= new Query();
			query1.addCriteria(Criteria.where("categoryName").is("all"));
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
}
