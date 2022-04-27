package com.divatt.admin.services;

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


	public List<SpecificationEntity> listOfSpecification(String gender) {
		try
		{
			Query query= new Query();
			query.addCriteria(Criteria.where("categoryName").is(gender));
			List<SpecificationEntity> listOfSpecificationData=mongoOperations.find(query, SpecificationEntity.class);
			return listOfSpecificationData;
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
