package com.divatt.admin.servicesImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divatt.admin.constant.MessageConstant;
import com.divatt.admin.constant.RestTemplateConstants;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.product.ProductEntity;
import com.divatt.admin.entity.product.ProductEntity2;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.ProductRepo;
import com.divatt.admin.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${DESIGNER}")
	private String DESIGNER_SERVICE;

	@Value("${AUTH}")
	private String AUTH_SERVICE;

	@Value("${ADMIN}")
	private String ADMIN_SERVICE;

	@Value("${USER}")
	private String USER_SERVICE;

	
	public GlobalResponse productApproval(int productId, int designerId, List<Object> commString,
			String ApprovedBy, String adminStatus) {
		try {
			ResponseEntity<ProductEntity2> exchange = restTemplate.exchange(
					DESIGNER_SERVICE +"designerProducts/productAdmin/" + productId, HttpMethod.GET, null,
					ProductEntity2.class);
			
			ProductEntity2 productdata = exchange.getBody();
			if (productdata.getDesignerId().equals(designerId)) {

				productdata.getProductStageDetails().setApprovedBy(ApprovedBy);
				productdata.getProductStageDetails().setApprovedOn(new Date());
				productdata.getProductStageDetails().setComment(commString);
				productdata.setIsActive(true);
				productdata.setAdminStatus(adminStatus);
				productdata.setProductStage(adminStatus+" By Admin");

				restTemplate.put(DESIGNER_SERVICE+RestTemplateConstants.DESIGNER_PRODUCTS_APPROVAL_UPDATE + productId, productdata,
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
