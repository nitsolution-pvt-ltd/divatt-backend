package com.divatt.admin.services;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.product.ProductEntity;
import com.divatt.admin.exception.CustomException;


@Service
public class ProductService {

	@Autowired
	private MongoTemplate mongoTemplate;

	public GlobalResponse productApproval(Integer productId, Integer designerId, String comment, String ApprovedBy,
			String adminStatus) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ProductEntity> exchange = restTemplate.exchange(
					"http://localhost:8083/dev/designerProduct/view/" + productId, HttpMethod.GET, null,
					ProductEntity.class);
			ProductEntity productdata = exchange.getBody();
			if (productdata.getDesignerId().equals(designerId)) {

				productdata.setApprovedBy(ApprovedBy);
				productdata.setApprovedOn(new Date());
				productdata.setComments(comment);
				productdata.setIsActive(true);
				productdata.setAdminStatus(adminStatus);
				productdata.setAdminStatusOn(new Date());

				String status = null;
				if (adminStatus.equals("Approved")) {
					status = "approved";
				} else if (adminStatus.equals("Rejected")) {
					status = "rejected";
				} else {
					status = "pending";
				}
				restTemplate.put("Http://localhost:8083/dev/designerProduct/update/" + productId, productdata,
						String.class);
				return new GlobalResponse("Status Updated", "Product " + status + " successfully", 200);
			} else {
				return new GlobalResponse("Bad Request", "ProductID and designerId are mismatched", 400);
			}
			// return null;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
