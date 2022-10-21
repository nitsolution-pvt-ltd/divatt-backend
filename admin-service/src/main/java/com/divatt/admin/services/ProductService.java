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

    @SuppressWarnings({ "null", "unchecked" })
    public Object getorderItemStatus(String orderItemStatus, int page, int limit, String sort,
            Boolean isDeleted,
            Optional<String> sortBy) {
        try {
            List<String> order = new ArrayList<>();
            Map<String, Object> orderdetails = new HashMap<>();
            List<Object> data = new ArrayList<>();

//            Pageable pageable=null;
//            if (sort.equals("ASC")) {
//                PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(orderItemStatus));
//            } else {
//                PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(orderItemStatus));
//            }
//            
//            Page<OrderSKUDetailsEntity> findall=null;
//            int totalPage=findall.getTotalPages()-1;
//            if(totalPage<0) {
//                totalPage=0;
//            }

            LOGGER.info("Inside OrderService - getorderItemStatus()");
            LOGGER.info(orderItemStatus);
            String url = "https://localhost:8082/dev/user/find?orderItemStatus=" + orderItemStatus
                    + "&page=" + page + "&limit=" + limit + "&sort=" + sort + "&isDeleted=" + isDeleted + "&keyword="
                    + "&sortBy=" + sortBy.get();

            LOGGER.info("Final URL is: {}", url);
            @SuppressWarnings("null")
            String body = restTemplate
                    .getForEntity(url,
                            JSONObject.class)
                    .getBody().toString();
            LOGGER.info("get data from 1st REST call: " + body);
            
            JSONObject body1 = restTemplate
                    .getForEntity(url,
                            JSONObject.class)
                    .getBody();

            LOGGER.info("get data from 2nd REST call: " + body1);
            data.add(body1);
            
            LOGGER.info(data.toString());

            JSONObject jsonObject = (JSONObject) JSONValue.parse(body);
            LOGGER.info(jsonObject.toString());

            String content = jsonObject.get("data").toString();
            LOGGER.info("Inside content"+jsonObject.get("data").toString());
           JSONArray array=new JSONArray(content);
           LOGGER.info("Inside array"+array.toString());
           
           for(int i=0;i<array.length();i++) {
            ObjectMapper objectMapper=new ObjectMapper();
            OrderSKUDetailsEntity readValue = objectMapper.readValue( array.get(i).toString(), OrderSKUDetailsEntity.class);
           
            LOGGER.info("inside body1"+body1.toString());
            
            

            order.add(readValue.getOrderId());
            
           
         
                LOGGER.info("Inside OrderId "+readValue.getOrderId());
           }
           
           LOGGER.info(order.toString());
           
           for(String id:order) {
               String url2="https://localhost:8082/dev/userOrder/findbyOrderIds?orderId=" + id
                       + "&page=" + page + "&limit=" + limit + "&sort=" + sort + "&isDeleted=" + isDeleted + "&keyword="
                       + "&sortBy=" + sortBy.get();
               
                JSONObject forObject = restTemplate.getForObject(
                        "https://localhost:8082/dev/userOrder/getorderByid1/" + id,
                        JSONObject.class);
                JSONObject object = restTemplate.getForObject(
                        url2,
                        JSONObject.class);
                
                body1.put("PaymentMode", forObject.get("paymentMode"));
                body1.put("OrderPaymentdetails", forObject.get("paymentDetails"));
              
                data.add(object);
                
//            }
         

            LOGGER.info(orderdetails.toString());
            
            
           }
           
//            OrderSKUDetailsEntity body1 = restTemplate
//                    .getForEntity("https://localhost:8082/dev/user/find?orderItemStatus=" + orderItemStatus
//                            + "&page=" + page + "&limit=" + limit + "&sort=" + sort + "&isDeleted=" + isDeleted + "&keyword="
//                            + "&sortBy=" + sortBy.get(),
//                            OrderSKUDetailsEntity.class).getBody();
 //           JSONArray jsonArray= new JSONArray(data);
//            for(int i=0;i<jsonObject.length();i++) {
//            ObjectMapper objectMapper=new ObjectMapper();
//            
//            OrderSKUDetailsEntity orderId=objectMapper.readValue(jsonArray.get(i).toString(), OrderSKUDetailsEntity.class);
//            LOGGER.info(orderId.toString());
 //           }
          //  OrderSKUDetailsEntity readValue = objectMapper.readValue(new URL(url), Object.class);
          // LOGGER.info(orderId.getOrderId());
            
  //          LOGGER.info(orderdetails.toString());
//            LOGGER.info(body.size() + "");
//
//            for (int i = 0; i < body.size(); i++) {
//                JSONObject jsonObject2 = (JSONObject) body.get(i);
//                String orderId1 = (String) jsonObject2.get("content");
//            //    LOGGER.info(+"");
//                order.add(orderId1);
//                LOGGER.info("List" + order.toString());
//            }
//            LOGGER.info("new" + order.toString());
//            LOGGER.info("Outside Loop" + order.toString());
//
//            for (String id : order) {
//                LOGGER.info(id);
           data.add(orderdetails);
            
           
         
            return data;

//       for(int i=0;i<orderdetails.size();i++) {
//          LOGGER.info("Difference list"+orderdetails.get(i).toString());

//      List<OrderSKUDetailsEntity> order = (List<OrderSKUDetailsEntity>) body;
//      List<ProductMasterEntity> list=new ArrayList<>();
//  LOGGER.info(order.get(0).getOrderId());
//       order.forEach(o -> {
//           LOGGER.info(o.getOrderId());
//           
////            ProductMasterEntity forObject = restTemplate.getForObject( "https://65.1.190.195:8082/dev/userOrder/orderDetails/"+o.getOrderId(), ProductMasterEntity.class);
////            list.add(forObject);
//       });
            // LOGGER.info(list.toString());

        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

}
