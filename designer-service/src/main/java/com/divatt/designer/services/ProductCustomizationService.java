package com.divatt.designer.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divatt.designer.entity.ListProduct;
import com.divatt.designer.entity.product.ProductCustomizationEntity;
import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.entity.profile.DesignerProfileEntity;
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
	public ProductCustomizationEntity viewChartService(String productName) {
		try {
			Query query= new Query();
			query.addCriteria(Criteria.where("productName").is(productName));
			ProductCustomizationEntity chartDetails=mongoOperations.findOne(query, ProductCustomizationEntity.class);
			return chartDetails;
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public GlobalResponce updateService(String productName, ProductCustomizationEntity productCustomizationEntity) {
		try {
			Query query= new Query();
			query.addCriteria(Criteria.where("productName").is(productName));
			List<ProductCustomizationEntity> listProduct=mongoOperations.find(query, ProductCustomizationEntity.class);
			if(listProduct.isEmpty())
			{
				return new GlobalResponce("Error!!", "Product Does not exist", 400);
			}
			productCustomizationEntity.setProductChartId(listProduct.get(0).getProductChartId());
			productCustomizationRepo.save(productCustomizationEntity);
			return new GlobalResponce("Success", "Product chart data successfully updated", 200);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	public Map<String, Object> getChartDetails(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy, String categoryName) {
		try {
			int CountData = (int) productCustomizationRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<ProductCustomizationEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = productCustomizationRepo.findByCategoryName(categoryName, pagingSort);
			} else {
				findAll = productCustomizationRepo.Search(keyword, pagingSort);

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
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
