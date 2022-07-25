package com.divatt.admin.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.product.ProductEntity;
import com.divatt.admin.exception.CustomException;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import springfox.documentation.spring.web.json.Json;

@Service
public class ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private RestTemplate restTemplate;

	public GlobalResponse productApproval(Integer productId, Integer designerId, String comment, String ApprovedBy,
			String adminStatus) {
		try {
			ResponseEntity<ProductEntity> exchange = restTemplate.exchange(
					"https://localhost:8083/dev/designerProduct/view/" + productId, HttpMethod.GET, null,
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
				// System.out.println(productdata);
				restTemplate.put("https://localhost:8083/dev/designerProduct/approval/" + productId, productdata,
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

//	public ResponseEntity<?> designerOrderListService(Integer page, Integer limit, String keyword)
//			{
//		LOGGER.info("Inside - ProductServicer.designerOrderListService()");
//		try {
//
//			JsonObject wishlistObj = new JsonObject();
//
//			wishlistObj.addProperty("limit", limit);
//			wishlistObj.addProperty("page", page);
//			wishlistObj.addProperty("keyword", keyword);
//			System.out.println("dd");
//			HttpResponse<JsonNode> response = null;
//
//			Unirest.setTimeouts(0, 0);
//			response = Unirest.get("http://localhost:8082/dev/userOrder/list")
//					.header("Content-Type", "application/json").queryString("limit",limit)
//					.queryString("page",page)
//					.queryString("keyword",keyword)
//					.asJson();
//
//			return ResponseEntity.ok(new Json(response.getBody().toString()));
//		} catch (Exception e) {
//			throw new CustomException(e.getMessage());
//		}
//
//	}

}
