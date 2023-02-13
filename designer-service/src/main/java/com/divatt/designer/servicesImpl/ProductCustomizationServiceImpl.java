package com.divatt.designer.servicesImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.divatt.designer.constant.MessageConstant;
import com.divatt.designer.entity.product.ProductCustomizationEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.repo.ProductCustomizationRepo;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.ProductCustomizationService;
import com.divatt.designer.services.SequenceGenerator;

@Service
public class ProductCustomizationServiceImpl implements ProductCustomizationService{

	@Autowired
	private ProductCustomizationRepo productCustomizationRepo;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private SequenceGenerator sequenceGenerator;
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(ProductCustomizationServiceImpl.class);

	public GlobalResponce addCahrtService(ProductCustomizationEntity productCustomizationEntity) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("name").is(productCustomizationEntity.getProductName()));
			List<ProductCustomizationEntity> find = mongoOperations.find(query, ProductCustomizationEntity.class);
			if (find.isEmpty()) {
				productCustomizationEntity
						.setProductChartId(sequenceGenerator.getNextSequence(ProductCustomizationEntity.SEQUENCE_NAME));
				productCustomizationRepo.save(productCustomizationEntity);
				return new GlobalResponce(MessageConstant.SUCCESS.getMessage(), MessageConstant.PRODUCT_CHAT_DATA_ADD.getMessage(), 200);
			}
			return new GlobalResponce(MessageConstant.BAD_REQUEST.getMessage(), MessageConstant.NAME_ALREADY_EXIST.getMessage(), 200);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ProductCustomizationEntity viewChartService(String productName) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("productName").is(productName));
			ProductCustomizationEntity chartDetails = mongoOperations.findOne(query, ProductCustomizationEntity.class);
			return chartDetails;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponce updateService(String productName, ProductCustomizationEntity productCustomizationEntity) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("productName").is(productName));
			List<ProductCustomizationEntity> listProduct = mongoOperations.find(query,
					ProductCustomizationEntity.class);
			if (listProduct.isEmpty()) {
				return new GlobalResponce(MessageConstant.ERROR.getMessage(), MessageConstant.PRODUCT_NOT_FOUND.getMessage(), 400);
			}
			productCustomizationEntity.setProductChartId(listProduct.get(0).getProductChartId());
			productCustomizationRepo.save(productCustomizationEntity);
			return new GlobalResponce(MessageConstant.SUCCESS.getMessage(), MessageConstant.PRODUCT_CHAT_DATA_UPDATE.getMessage(), 200);
		} catch (Exception e) {
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
				throw new CustomException(MessageConstant.PRODUCT_NOT_FOUND.getMessage());
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
