package com.divatt.admin.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.ProductMeasurementEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.MeasurementRepo;

@Service
public class ProductMeasurementService {

	@Autowired
	private MeasurementRepo measurementRepo;
	
	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	@Autowired
	private MongoOperations mongoOperations;
	public GlobalResponse addProductMeasurement(ProductMeasurementEntity productMeasurementEntity) {
		try
		{
		Query query= new Query();
		query.addCriteria(Criteria.where("subCategoryName").is(productMeasurementEntity.getSubCategoryName()));
		List<ProductMeasurementEntity> listData=mongoOperations.find(query, ProductMeasurementEntity.class);
			if(listData.isEmpty())
			{
				productMeasurementEntity.setId(sequenceGenerator.getNextSequence(ProductMeasurementEntity.SEQUENCE_NAME));
				measurementRepo.save(productMeasurementEntity);
				return new GlobalResponse("Success", "Measurement Added Successfully", 200);
			}
			else
			{
				throw new CustomException("SubCategory name allready exists");
			}
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public ProductMeasurementEntity viewProductDetails(String categoryName, String subCategoryName) {
		try {
			Query query= new Query();
			query.addCriteria(Criteria.where("categoryName").is(categoryName).and("subCategoryName").is(subCategoryName));
			ProductMeasurementEntity productMeasurementEntity= mongoOperations.findOne(query, ProductMeasurementEntity.class);
			return productMeasurementEntity;
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	
}
