package com.divatt.admin.servicesImpl;

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

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.ProductMeasurementEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.MeasurementRepo;
import com.divatt.admin.services.ProductMeasurementService;
import com.divatt.admin.services.SequenceGenerator;

@Service
public class ProductMeasurementServiceImpl implements ProductMeasurementService {

	@Autowired
	private MeasurementRepo measurementRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private MongoOperations mongoOperations;

	public GlobalResponse addProductMeasurement(ProductMeasurementEntity productMeasurementEntity) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("subCategoryName").is(productMeasurementEntity.getSubCategoryName())
					.and("categoryName").is(productMeasurementEntity.getCategoryName()));
			List<ProductMeasurementEntity> listData = mongoOperations.find(query, ProductMeasurementEntity.class);
			if (listData.isEmpty()) {
				productMeasurementEntity
						.setId(sequenceGenerator.getNextSequence(ProductMeasurementEntity.SEQUENCE_NAME));
				productMeasurementEntity.setIsActive(false);
				productMeasurementEntity.setIsDelete(false);
				measurementRepo.save(productMeasurementEntity);
				return new GlobalResponse("Success", "Measurement Added Successfully", 200);

			}

			else {
				throw new CustomException("SubCategory name allready exists");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ProductMeasurementEntity viewProductDetails(String categoryName, String subCategoryName) {
		try {
			Query query = new Query();
			query.addCriteria(
					Criteria.where("categoryName").is(categoryName).and("subCategoryName").is(subCategoryName));
			ProductMeasurementEntity productMeasurementEntity = mongoOperations.findOne(query,
					ProductMeasurementEntity.class);
			return productMeasurementEntity;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getSpecificationDetails(int page, int limit, String sort, String sortName,
			String keyword, Optional<String> sortBy, Boolean isDelete, String metakey) {
		try {

			Query query = new Query();
			query.addCriteria(Criteria.where("metaKey").is("PRODUCT_MEASUREMENTS"));
			List<ProductMeasurementEntity> productList = mongoOperations.find(query, ProductMeasurementEntity.class);
			int CountData = productList.size();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<ProductMeasurementEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = measurementRepo.findByIsDeleteAndMetaKey(isDelete, metakey, pagingSort);
			} else {
				findAll = measurementRepo.Search(keyword, metakey, isDelete, pagingSort);

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
				throw new CustomException("Measurement data not found");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse updateStatus(String metaKey, Integer id) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(id).and("metaKey").is("PRODUCT_MEASUREMENTS"));
			ProductMeasurementEntity productMeasurementEntity = mongoOperations.findOne(query,
					ProductMeasurementEntity.class);
			if (productMeasurementEntity != null) {
				if (productMeasurementEntity.getIsActive().equals(false)) {
					productMeasurementEntity.setIsActive(true);
					measurementRepo.save(productMeasurementEntity);
					return new GlobalResponse("Suucess", "Measurement status active", 200);
				} else {
					productMeasurementEntity.setIsActive(false);
					measurementRepo.save(productMeasurementEntity);
					return new GlobalResponse("Suucess", "Measurement status deactive", 200);
				}
			} else {
				return new GlobalResponse("Error!!", "Measurement Id does not exist", 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse deletemeasurementService(String metaKey, Integer id) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(id).and("metaKey").is("PRODUCT_MEASUREMENTS"));
			ProductMeasurementEntity productMeasurementEntity = mongoOperations.findOne(query,
					ProductMeasurementEntity.class);
			if (productMeasurementEntity != null) {
				if (productMeasurementEntity.getIsDelete().equals(false)) {
					productMeasurementEntity.setIsActive(false);
					productMeasurementEntity.setIsDelete(true);
					measurementRepo.save(productMeasurementEntity);
					return new GlobalResponse("Suucess", "Measurement deleted successfully", 200);
				} else {
					productMeasurementEntity.setIsActive(false);
					productMeasurementEntity.setIsDelete(false);
					measurementRepo.save(productMeasurementEntity);
					return new GlobalResponse("Suucess", "Measurement recover succesfully", 200);
				}
			} else {
				return new GlobalResponse("Error!!", "Measurement Id does not exist", 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse MeasurementUpdateService(@Valid ProductMeasurementEntity productMeasurementEntity,
			Integer measurementId) {
		try {
			if (measurementRepo.existsById(measurementId)) {
				ProductMeasurementEntity measurementData = measurementRepo.findById(measurementId).get();
				productMeasurementEntity.setId(measurementId);
				productMeasurementEntity.setIsActive(measurementData.getIsActive());
				productMeasurementEntity.setIsDelete(measurementData.getIsDelete());
				productMeasurementEntity.setMetaKey(measurementData.getMetaKey());
				measurementRepo.save(productMeasurementEntity);
				return new GlobalResponse("Success", "Measurement updated", 200);
			} else {
				return new GlobalResponse("Error!!", "Measurement Id does not exist", 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
