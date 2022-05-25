package com.divatt.user.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.orderPaymenRepo.UserOrderPaymentRepo;
import com.divatt.user.response.GlobalResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import springfox.documentation.spring.web.json.Json;

@Service
public class OrderAndPaymentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderAndPaymentService.class);

	HttpResponse<String> response = null;

	@Autowired
	private UserOrderPaymentRepo userOrderPaymentRepo;

	@Autowired
	private OrderDetailsRepo orderDetailsRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private Environment env;

	protected String getRandomString() {
//		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 16) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	protected String getRandomNumber() {
//		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String SALTCHARS = "1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 16) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public ResponseEntity<?> postRazorpayOrderCreateService(OrderDetailsEntity orderDetailsEntity) {
		LOGGER.info("Inside - OrderAndPaymentService.postRazorpayOrderCreateService()");

		try {
			RazorpayClient razorpayClient = new RazorpayClient(env.getProperty("key"), env.getProperty("secretKey"));
			JSONObject options = new JSONObject();

			options.put("amount", orderDetailsEntity.getTotalAmount());
			options.put("currency", "INR");
			options.put("receipt", "RC" + getRandomString());

			Order order = razorpayClient.Orders.create(options);

			return ResponseEntity.ok(new Json(order.toString()));

		} catch (RazorpayException e) {
			throw new CustomException(e.getMessage());
		}

	}

	public void postOrderPaymentService(OrderPaymentEntity orderPaymentEntity) {
		LOGGER.info("Inside - OrderAndPaymentService.postOrderPaymentService()");

		try {

			RazorpayClient razorpayClient = new RazorpayClient(env.getProperty("key"), env.getProperty("secretKey"));

//			List<Payment> payments = razorpayClient.Payments.fetchAll();
			OrderPaymentEntity filterCatDetails = new OrderPaymentEntity();

			filterCatDetails.setId(sequenceGenerator.getNextSequence(OrderPaymentEntity.SEQUENCE_NAME));
			filterCatDetails.setOrderId(orderPaymentEntity.getOrderId());
			filterCatDetails.setPaymentMode(orderPaymentEntity.getPaymentMode());
			filterCatDetails.setPaymentDetails(orderPaymentEntity.getPaymentDetails());
			filterCatDetails.setPaymentResponse(orderPaymentEntity.getPaymentResponse());
			filterCatDetails.setPaymentStatus(orderPaymentEntity.getPaymentStatus());
			filterCatDetails.setUserId(orderPaymentEntity.getUserId());
			filterCatDetails.setCreatedOn(new Date());

			userOrderPaymentRepo.save(filterCatDetails);

		} catch (RazorpayException e) {
			throw new CustomException(e.getMessage());
		}

	}

	public Map<String, Object> getOrderPaymentService(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy) {
		LOGGER.info("Inside - OrderAndPaymentService.getOrderPaymentService()");
		try {
			int CountData = (int) userOrderPaymentRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<OrderPaymentEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = userOrderPaymentRepo.findAll(pagingSort);
			} else {
				findAll = userOrderPaymentRepo.Search(keyword, pagingSort);

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
				throw new CustomException("Payment not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Map<String, Object> getOrders(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy) {
		LOGGER.info("Inside - OrderAndPaymentService.getOrders()");
		try {
			int CountData = (int) orderDetailsRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<OrderDetailsEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = orderDetailsRepo.findAll(pagingSort);
			} else {
				findAll = orderDetailsRepo.Search(keyword, pagingSort);

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
				throw new CustomException("Payment not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getOrderDetailsService(String orderId) {
		try {
			List<OrderDetailsEntity> findById = this.orderDetailsRepo.findByOrderId(orderId);
			List<OrderDetailsEntity> OrderPaymentRow = this.userOrderPaymentRepo.findByOrderId(orderId);

			List<Object> products = findById.get(0).getProducts();
			List<Integer> productId = new ArrayList<>();

			products.forEach(e -> {
				ObjectMapper obj = new ObjectMapper();
				String productIdFilter = null;
				try {
					productIdFilter = obj.writeValueAsString(e);
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}

				JsonNode cartJN = new JsonNode(productIdFilter);
				JSONObject object = cartJN.getObject();
				productId.add(Integer.parseInt(object.get("productId").toString()));

			});

			try {

				if (productId != null) {
					Unirest.setTimeouts(0, 0);
					response = Unirest.post("http://localhost:8083/dev/designerProduct/getProductListById")
							.header("Content-Type", "application/json").body(productId.toString()).asString();
				}
			} catch (Exception e2) {
				throw new CustomException("Product id not found");
			}

			try {

				List<Object> l1 = new ArrayList<>();
				int index = 0;
				products.forEach(e -> {
					ObjectMapper obj = new ObjectMapper();
					String productRowAppend = null;

					try {
						productRowAppend = obj.writeValueAsString(e);
					} catch (JsonProcessingException e2) {
						e2.printStackTrace();
					}
					JsonNode jn = new JsonNode(productRowAppend);
					JSONObject object = jn.getObject();

					JSONArray jsonArray = new JSONArray(response.getBody());

					ObjectMapper obj1 = new ObjectMapper();
					String writeValueAsString1 = null;

					try {
						writeValueAsString1 = obj1.writeValueAsString(OrderPaymentRow.get(0));
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}
					JsonNode cartJN = new JsonNode(writeValueAsString1);
					JSONObject cartObject = cartJN.getObject();
					object.put("productData", jsonArray.get(index));
					object.put("paymentData", cartObject);
					l1.add(object);
				});

				return ResponseEntity.ok(new Json(l1.toString()));
			} catch (Exception e2) {
				return ResponseEntity.ok(e2.getMessage());
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
//		return ResponseEntity.ok(null);
	}

	public ResponseEntity<?> getUserOrderDetailsService(Integer userId) {

		try {
			List<OrderDetailsEntity> findById = this.orderDetailsRepo.findByUserId(userId);
			List<OrderDetailsEntity> OrderPaymentRow = this.userOrderPaymentRepo.findByUserId(userId);

			
			List<Integer> productId = new ArrayList<>();
			findById.forEach(pr ->{
				
			
			List<Object> products = pr.getProducts();
			

			products.forEach(e -> {
				ObjectMapper obj = new ObjectMapper();
				String productIdFilter = null;
				try {
					productIdFilter = obj.writeValueAsString(e);
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}

				JsonNode cartJN = new JsonNode(productIdFilter);
				JSONObject object = cartJN.getObject();
				productId.add(Integer.parseInt(object.get("productId").toString()));

			});
			});
			try {

				if (productId != null) {
					Unirest.setTimeouts(0, 0);
					response = Unirest.post("http://localhost:8083/dev/designerProduct/getProductListById")
							.header("Content-Type", "application/json").body(productId.toString()).asString();
				}
			} catch (Exception e2) {
				throw new CustomException("Product id not found");
			}

			try {
				
				List<Object> l1 = new ArrayList<>();
				findById.forEach(pr ->{

				
				int index = 0;
				pr.getProducts().forEach(e -> {
					ObjectMapper obj = new ObjectMapper();
					String productRowAppend = null;

					try {
						productRowAppend = obj.writeValueAsString(e);
					} catch (JsonProcessingException e2) {
						e2.printStackTrace();
					}
					JsonNode jn = new JsonNode(productRowAppend);
					JSONObject object = jn.getObject();

					JSONArray jsonArray = new JSONArray(response.getBody());

					ObjectMapper obj1 = new ObjectMapper();
					String writeValueAsString1 = null;

					try {
						writeValueAsString1 = obj1.writeValueAsString(OrderPaymentRow.get(index));
					} catch (JsonProcessingException e1) {
						e1.printStackTrace();
					}
					JsonNode cartJN = new JsonNode(writeValueAsString1);
					JSONObject cartObject = cartJN.getObject();
					object.put("productData", jsonArray.get(index));
					object.put("paymentData", cartObject);
					l1.add(object);
				});
				});

				return ResponseEntity.ok(new Json(l1.toString()));
			} catch (Exception e2) {
				return ResponseEntity.ok(e2.getMessage());
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
