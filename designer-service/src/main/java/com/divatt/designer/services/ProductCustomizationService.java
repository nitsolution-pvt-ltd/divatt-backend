package com.divatt.designer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divatt.designer.entity.product.ProductCustomizationEntity;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.repo.ProductCustomizationRepo;
import com.divatt.designer.response.GlobalResponce;

@Service
public class ProductCustomizationService {
	
	@Autowired
	private ProductCustomizationRepo productCustomizationRepo;

	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private SequenceGenerator sequenceGenerator;
	public GlobalResponce addCahrtService(ProductCustomizationEntity productCustomizationEntity) {
		try {
			Query query= new Query();
			query.addCriteria(Criteria.where("name").is(productCustomizationEntity.getProductName()));
			List<ProductCustomizationEntity> find = mongoOperations.find(query,ProductCustomizationEntity.class);
			if(find.isEmpty())
			{
				productCustomizationEntity.setProductChartId(sequenceGenerator.getNextSequence(ProductCustomizationEntity.SEQUENCE_NAME));
				productCustomizationRepo.save(productCustomizationEntity);
				return new GlobalResponce("Success!!", "Product chat data Added susccessfully", 200);
			}
			return new GlobalResponce("Bad Request", "Product name already exist", 200);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
