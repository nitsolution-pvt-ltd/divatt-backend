package com.divatt.admin.services;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.sagemaker.model.SortBy;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.OrderSKUDetailsEntity;
import com.divatt.admin.entity.hsnCode.HsnEntity;
import com.divatt.admin.entity.product.ProductEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.ProductRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    private ProductRepo productRepo;

    @Autowired
    private RestTemplate restTemplate;

    public GlobalResponse productApproval(Integer productId, Integer designerId, List<Object> commString,
            String ApprovedBy,
            String adminStatus) {
        try {
            ResponseEntity<ProductEntity> exchange = restTemplate.exchange(
                    "https://localhost:8083/dev/designerProduct/view/" + productId, HttpMethod.GET, null,
                    ProductEntity.class);
            ProductEntity productdata = exchange.getBody();
            if (productdata.getDesignerId().equals(designerId)) {

                productdata.setApprovedBy(ApprovedBy);
                productdata.setApprovedOn(new Date());
                productdata.setComments(commString);
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

    public List<JSONObject> getReportSheet(Date startDate, Date endDate) {
        try {
            // ResponseEntity<JSONObject>
            // responseData=restTemplate.getForEntity("http://localhost:80", null)
            return null;
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

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
