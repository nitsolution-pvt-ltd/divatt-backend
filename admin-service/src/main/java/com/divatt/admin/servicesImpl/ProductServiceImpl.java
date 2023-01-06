package com.divatt.admin.servicesImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divatt.admin.constant.MessageConstant;
import com.divatt.admin.constant.RestTemplateConstant;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.product.ProductEntity;
import com.divatt.admin.entity.product.ProductEntity2;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.ProductRepo;
import com.divatt.admin.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
//	@Autowired
//	private MongoTemplate mongoTemplate;
	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private RestTemplate restTemplate;

	
	public GlobalResponse productApproval(int productId, int designerId, List<Object> commString,
			String ApprovedBy, String adminStatus) {
		try {
			ResponseEntity<ProductEntity2> exchange = restTemplate.exchange(
					RestTemplateConstant.DESIGNER_PRODUCTS_PRODUCT_LIST.getMessage() + productId, HttpMethod.GET, null,
					ProductEntity2.class);
			ProductEntity2 productdata = exchange.getBody();
			LOGGER.info("Product data is = {}",productdata);
			if (productdata.getDesignerId().equals(designerId)) {

				productdata.getProductStageDetails().setApprovedBy(ApprovedBy);;
				productdata.getProductStageDetails().setApprovedOn(new Date());
				productdata.getProductStageDetails().setComment(commString);
				productdata.setIsActive(true);
				productdata.setAdminStatus(adminStatus);
				productdata.setProductStage(adminStatus+" By Admin");

//				String status = null;
//				if (adminStatus.equals("Approved")) {
//					status = "approved";
//				} else if (adminStatus.equals("Rejected")) {
//					status = "rejected";
//				} else {
//					status = "pending";
//				}
				LOGGER.info("Data for update = {}",productdata);
				restTemplate.put(RestTemplateConstant.DESIGNER_PRODUCTS_APPROVAL_UPDATE.getMessage() + productId, productdata,
						String.class);

				return new GlobalResponse(MessageConstant.STATUS_UPDATED.getMessage(), MessageConstant.PRODUCT_STATUS_UPDATED.getMessage(), 200);
			} else {
				return new GlobalResponse(MessageConstant.BAD_REQUEST.getMessage(), MessageConstant.PRODUCT_AND_DESIGNER_ID_MISMATCH.getMessage(), 400);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public List<JSONObject> getReportSheet(Date startDate, Date endDate) {
		try {
			// ResponseEntity<JSONObject>
			// responseData=restTemplate.getForEntity("http://localhost:80", null)
			return null;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public Map<String, Object> getProductDetails() {
		try {

			Pageable pagingSort = PageRequest.of(0, 10);
			Page<ProductEntity> findAllActive = productRepo.findByStatus(false, true, pagingSort);
			Page<ProductEntity> findAllInActive = productRepo.findByStatus(false, false, pagingSort);
			Page<ProductEntity> findAllDeleted = productRepo.findByIsDelete(true, pagingSort);
			Map<String, Object> response = new HashMap<>();
			response.put("activeProduct", findAllActive.getTotalElements());
			response.put("inActiveProduct", findAllInActive.getTotalElements());
			response.put("deleted", findAllDeleted.getTotalElements());
			return response;

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
