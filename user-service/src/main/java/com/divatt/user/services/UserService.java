package com.divatt.user.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.divatt.user.entity.UserDesignerEntity;
import com.divatt.user.entity.PCommentEntity.ProductCommentEntity;
import com.divatt.user.entity.cart.UserCartEntity;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
import com.divatt.user.entity.wishlist.WishlistEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.UserDesignerRepo;
import com.divatt.user.repo.cart.UserCartRepo;
import com.divatt.user.repo.orderPaymenRepo.UserOrderPaymentRepo;
import com.divatt.user.repo.pCommentRepo.ProductCommentRepo;
import com.divatt.user.repo.wishlist.WishlistRepo;
import com.divatt.user.response.GlobalResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import springfox.documentation.spring.web.json.Json;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private WishlistRepo wishlistRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private UserCartRepo userCartRepo;

	@Autowired
	private ProductCommentRepo productCommentRepo;

	@Autowired
	private UserDesignerRepo userDesignerRepo;

	@Autowired
	private UserOrderPaymentRepo userOrderPaymentRepo;

	@Autowired
	private OrderDetailsRepo orderDetailsRepo;

	protected String getRandomString() {
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

	public GlobalResponse postWishlistService(ArrayList<WishlistEntity> wishlistEntity) {
		LOGGER.info("Inside - UserService.postWishlistService()");

		try {
			WishlistEntity filterCatDetails = new WishlistEntity();

			for (WishlistEntity getRow : wishlistEntity) {
				Optional<WishlistEntity> findByCategoryName = wishlistRepo
						.findByProductIdAndUserId(getRow.getProductId(), getRow.getUserId());
				if (wishlistEntity.size() <= 1 && findByCategoryName.isPresent()) {
					throw new CustomException("Wishlist already exist");
				}
				if (!findByCategoryName.isPresent()) {
					filterCatDetails.setId(sequenceGenerator.getNextSequence(WishlistEntity.SEQUENCE_NAME));
					filterCatDetails.setUserId(getRow.getUserId());
					filterCatDetails.setProductId(getRow.getProductId());
					filterCatDetails.setAddedOn(new Date());
					wishlistRepo.save(filterCatDetails);
				}
			}

			return new GlobalResponse("SUCCESS", "Wishlist added succesfully", 200);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse deleteWishlistService(Integer productId, Integer userId) {
		try {
			Optional<WishlistEntity> findByProductRow = wishlistRepo.findByProductIdAndUserId(productId, userId);
			if (!findByProductRow.isPresent()) {
				throw new CustomException("Product not exist!");
			} else {
				wishlistRepo.deleteByProductIdAndUserId(productId, userId);
				return new GlobalResponse("SUCCESS", "Wishlist removed succesfully", 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public Map<String, Object> getWishlistDetails(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy) {
		LOGGER.info("Inside - UserService.getWishlistDetails()");
		try {
			int CountData = (int) wishlistRepo.count();
			Pageable pagingSort = null;
			if (limit == 0) {
				limit = CountData;
			}

			if (sort.equals("ASC")) {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
			} else {
				pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
			}

			Page<WishlistEntity> findAll = null;

			if (keyword.isEmpty()) {
				findAll = wishlistRepo.findAll(pagingSort);
			} else {
				findAll = wishlistRepo.Search(keyword, pagingSort);

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
				throw new CustomException("Wishlist not found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getUserWishlistDetails(Integer userId, Integer page, Integer limit)
			throws UnirestException {
		LOGGER.info("Inside - UserService.getUserWishlistDetails()");
		try {
			List<WishlistEntity> findByUserId = wishlistRepo.findByUserId(userId);
			List<Integer> productIds = new ArrayList<>();

			findByUserId.forEach((e) -> {
				productIds.add(e.getProductId());
			});
			JsonObject wishlistObj = new JsonObject();

			wishlistObj.addProperty("productId", productIds.toString());
			wishlistObj.addProperty("limit", limit);
			wishlistObj.addProperty("page", page);
			System.out.println(wishlistObj);
			HttpResponse<JsonNode> response = null;
			if (productIds != null) {
				Unirest.setTimeouts(0, 0);
				response = Unirest.post("http://localhost:8083/dev/designerProduct/getWishlistProductList")
						.header("Content-Type", "application/json").body(wishlistObj.toString()).asJson();
			}
			return ResponseEntity.ok(new Json(response.getBody().toString()));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse postCartDetailsService(List<UserCartEntity> userCartEntity) {
		LOGGER.info("Inside - UserService.postCartDetailsService()");

		try {
			UserCartEntity filterCatDetails = new UserCartEntity();

			for (UserCartEntity getRow : userCartEntity) {
				Optional<UserCartEntity> findByCat = userCartRepo.findByProductIdAndUserId(getRow.getProductId(),
						getRow.getUserId());

				if (userCartEntity.size() <= 1 && findByCat.isPresent()) {
					throw new CustomException("Product already added to the cart.");
				} else {
					if (!findByCat.isPresent()) {
						filterCatDetails.setId(sequenceGenerator.getNextSequence(UserCartEntity.SEQUENCE_NAME));
						filterCatDetails.setUserId(getRow.getUserId());
						filterCatDetails.setProductId(getRow.getProductId());
						filterCatDetails.setQty(getRow.getQty());
						filterCatDetails.setAddedOn(new Date());
						userCartRepo.save(filterCatDetails);
					}
				}
			}
			return new GlobalResponse("SUCCESS", "Cart added succesfully", 200);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> putCartDetailsService(UserCartEntity userCartEntity) {
		LOGGER.info("Inside - UserService.putCartDetailsService()");

		try {

			Optional<UserCartEntity> findByCat = userCartRepo.findByProductIdAndUserId(userCartEntity.getProductId(),
					userCartEntity.getUserId());

			if (!findByCat.isPresent()) {
				throw new CustomException("Product not found in the cart.");
			} else {
				UserCartEntity RowsDetails = findByCat.get();
				RowsDetails.setUserId(userCartEntity.getUserId());
				RowsDetails.setProductId(userCartEntity.getProductId());
				RowsDetails.setQty(userCartEntity.getQty());
				RowsDetails.setAddedOn(new Date());
				UserCartEntity getdata = userCartRepo.save(RowsDetails);

				Map<String, Object> map = new HashMap<>();
				map.put("reason", "SUCCESS");
				map.put("message", "Cart updated succesfully");
				map.put("status", 200);
				map.put("qty", getdata.getQty());
				return ResponseEntity.ok(map);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse deleteCartService(Integer productId, Integer userId) {
		LOGGER.info("Inside - UserService.deleteCartService()");
		try {
			Optional<UserCartEntity> findByProductRow = userCartRepo.findByProductIdAndUserId(productId, userId);
			if (!findByProductRow.isPresent()) {
				throw new CustomException("Cart not exist!");
			} else {
				userCartRepo.deleteByProductIdAndUserId(productId, userId);
				return new GlobalResponse("SUCCESS", "Cart removed succesfully", 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> getUserCartDetailsService(Integer userId, Integer page, Integer limit)
			throws UnirestException {
		LOGGER.info("Inside - UserService.getUserCartDetailsService()");
		try {

			List<UserCartEntity> findByUserId = userCartRepo.findByUserId(userId);
			List<Integer> productIds = new ArrayList<>();

			findByUserId.forEach((e) -> {
				productIds.add(e.getProductId());
			});
			JsonObject cartObj = new JsonObject();

			cartObj.addProperty("productId", productIds.toString());
			cartObj.addProperty("limit", limit);
			cartObj.addProperty("page", page);

			if (productIds.isEmpty()) {
				return ResponseEntity
						.ok(new Json("{\"reason\": \"ERROR\", \"message\": \"Product not found!\",\"status\": 400}"));
			}

			try {
				Unirest.setTimeouts(0, 0);
				HttpResponse<JsonNode> response = Unirest
						.post("http://localhost:8083/dev/designerProduct/getCartProductList")
						.header("Content-Type", "application/json").body(cartObj.toString()).asJson();

				JSONArray array = response.getBody().getArray();

				List<Object> l1 = new ArrayList<>();
				array.forEach(e -> {
					JsonNode jn = new JsonNode(e.toString());
					JSONObject object = jn.getObject();

					object.put("cartData", userCartRepo.findByUserIdAndProductId(userId,
							Integer.parseInt(object.get("productId").toString())));
					l1.add(object);
				});

				return ResponseEntity.ok(new Json(l1.toString()));
			} catch (Exception e2) {
				return ResponseEntity.ok(e2.getMessage());
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@SuppressWarnings("rawtypes")
	public GlobalResponse postProductCommentService(ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserService.postWishlistService()");

		try {
			Optional<ProductCommentEntity> findByTitle = productCommentRepo
					.findByProductIdAndUserId(productCommentEntity.getProductId(), productCommentEntity.getUserId());
			if (findByTitle.isPresent()) {
				throw new CustomException("Reviewed already exist!");
			} else {
				ProductCommentEntity<?> RowsDetails = new ProductCommentEntity<Object>();

				RowsDetails.setId(sequenceGenerator.getNextSequence(ProductCommentEntity.SEQUENCE_NAME));
				RowsDetails.setUserId(productCommentEntity.getUserId());
				RowsDetails.setProductId(productCommentEntity.getProductId());
				RowsDetails.setRating(productCommentEntity.getRating());
				RowsDetails.setComment(productCommentEntity.getComment());
				RowsDetails.setIsVisible(false);
				RowsDetails.setUploads(productCommentEntity.getUploads());
				RowsDetails.setCreatedOn(new Date());

				productCommentRepo.save(RowsDetails);
				return new GlobalResponse("SUCCESS", "Reviewed added succesfully", 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@SuppressWarnings("rawtypes")
	public GlobalResponse putProductCommentService(ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserService.putProductCommentService()");

		try {

			Optional<ProductCommentEntity> findByRow = productCommentRepo.findById(productCommentEntity.getId());

			if (!findByRow.isPresent()) {
				throw new CustomException("Reviewed not exist!");
			} else {
				ProductCommentEntity<?> RowsDetails = findByRow.get();

				RowsDetails.setUserId(productCommentEntity.getUserId());
				RowsDetails.setProductId(productCommentEntity.getProductId());
				RowsDetails.setRating(productCommentEntity.getRating());
				RowsDetails.setComment(productCommentEntity.getComment());
				RowsDetails.setIsVisible(false);
				RowsDetails.setUploads(productCommentEntity.getUploads());
				RowsDetails.setCreatedOn(new Date());

				productCommentRepo.save(RowsDetails);
				return new GlobalResponse("SUCCESS", "Reviewed updated succesfully", 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	public GlobalResponse putProductCommentStatusService(ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserService.putProductCommentStatusService()");

		try {

			Optional<ProductCommentEntity> findByRow = productCommentRepo.findById(productCommentEntity.getId());

			if (!findByRow.isPresent()) {
				throw new CustomException("Reviewed not exist!");
			} else {
				ProductCommentEntity<?> RowsDetails = findByRow.get();
				Boolean Status = false;
				String message = null;
				if (findByRow.get().getIsVisible() == true) {
					Status = false;
					message = "actived";
				} else {
					Status = true;
					message = "inactive";
				}
				RowsDetails.setIsVisible(Status);
				RowsDetails.setCreatedOn(new Date());

				productCommentRepo.save(RowsDetails);
				return new GlobalResponse("SUCCESS", "Reviewed status " + message + " successfully", 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@SuppressWarnings("rawtypes")
	public GlobalResponse deleteProductCommentService(Integer Id) {
		try {
			Optional<ProductCommentEntity> findByProductRow = productCommentRepo.findById(Id);
			if (!findByProductRow.isPresent()) {
				throw new CustomException("Reviewed not exist!");
			} else {
				productCommentRepo.deleteById(Id);
				return new GlobalResponse("SUCCESS", "Reviewed removed succesfully", 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> getProductUser() {
		try {

			RestTemplate restTemplate = new RestTemplate();

			String body = restTemplate
					.getForEntity("http://localhost:8083/dev/designerProduct/userProductList", String.class).getBody();

			Json js = new Json(body);
			return ResponseEntity.ok(js);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> postfollowDesignerService(@Valid UserDesignerEntity userDesignerEntity) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			userDesignerRepo.findByUserId(userDesignerEntity.getUserId()).ifPresentOrElse((e) -> {
				userDesignerEntity.setId(e.getId());
			}, () -> {
				userDesignerEntity.setId(sequenceGenerator.getNextSequence(UserDesignerEntity.SEQUENCE_NAME));
			});

			userDesignerEntity.setCreatedOn(date.toString());
			UserDesignerEntity save = userDesignerRepo.save(userDesignerEntity);
			if (save != null && save.getIsFollowing() == true)
				return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Follow successfully", 200));
			else if (save != null && save.getIsFollowing() == false)
				return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Unfollow successfully", 200));
			else
				throw new CustomException("Something went wrong! try again later");
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getDesignerDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<?> Response = restTemplate
					.getForEntity("http://localhost:8083/dev/designerProduct/getDesignerProductListUser?page=" + page
							+ "&limit=" + limit + "&", String.class);
			Json jsons = new Json((String) Response.getBody());
			return ResponseEntity.ok(jsons);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> productDetails(Integer productId) {
		try {

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> exchange = restTemplate.exchange(
					"http://localhost:8083/dev/designerProduct/view/" + productId, HttpMethod.GET, null, String.class);
			Json js = new Json(exchange.getBody());
			return ResponseEntity.ok(js);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> getDesignerUser() {
		try {

			RestTemplate restTemplate = new RestTemplate();

			String body = restTemplate.getForEntity("http://localhost:8083/dev/designer/userDesignerList", String.class)
					.getBody();

			Json js = new Json(body);
			return ResponseEntity.ok(js);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getDesignerProfileDetailsService(Integer designerId, Long userId) {
		try {

			RestTemplate restTemplate = new RestTemplate();

			String body = restTemplate
					.getForEntity("http://localhost:8083/dev/designer/user/" + designerId, String.class).getBody();
			JsonNode jn = new JsonNode(body);
			JSONObject object = jn.getObject();
			object.put("follwerCount", userDesignerRepo
					.findByDesignerIdAndIsFollowing(Long.parseLong(object.get("dId").toString()), true).size());
			
			if(userId != 0l)
				object.put("UserDesigner", userDesignerRepo.findByUserId(userId).get());
			
			
			return ResponseEntity.ok(new Json(object.toString()));

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getPerDesignerProductListService(int page, int limit, String sort, String sortName,
			Boolean isDeleted, String keyword, Optional<String> sortBy, Integer designerId) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<?> Response = restTemplate
					.getForEntity("http://localhost:8083/dev/designerProduct/getPerDesignerProductUser/" + designerId
							+ "?page=" + page + "&limit=" + limit + "&", String.class);
			Json jsons = new Json((String) Response.getBody());
			return ResponseEntity.ok(jsons);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public GlobalResponse postOrderPaymentService(OrderPaymentEntity orderPaymentEntity) {
		LOGGER.info("Inside - UserService.postOrderPaymentService()");

		try {

			RazorpayClient razorpayClient = new RazorpayClient("rzp_test_q5ch2uQXmBRynp", "AYotTQdNFtrVXwHSyskFCB2o");
			JSONObject options = new JSONObject();
			options.put("amount", 50);
			options.put("currency", "INR");
			options.put("receipt", "RC" + getRandomString());
			Order order = razorpayClient.Orders.create(options);

			List<Payment> payments = razorpayClient.Payments.fetchAll();
			OrderPaymentEntity filterCatDetails = new OrderPaymentEntity();

			filterCatDetails.setId(sequenceGenerator.getNextSequence(OrderPaymentEntity.SEQUENCE_NAME));
			filterCatDetails.setOrderId("OR" + getRandomString());
			filterCatDetails.setPaymentMode(orderPaymentEntity.getPaymentMode());
			filterCatDetails.setPaymentDetails(payments);
			filterCatDetails.setPaymentResponse(order);
			filterCatDetails.setPaymentStatus(orderPaymentEntity.getPaymentStatus());
			filterCatDetails.setCreatedOn(new Date());

			userOrderPaymentRepo.save(filterCatDetails);
			return new GlobalResponse("SUCCESS", "Order placed succesfully", 200);

		} catch (RazorpayException e) {
			throw new CustomException(e.getMessage());
		}

	}

	public Map<String, Object> getOrderPaymentService(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy) {
		LOGGER.info("Inside - UserService.getOrderPaymentService()");
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
		LOGGER.info("Inside - UserService.getOrders()");
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

}
