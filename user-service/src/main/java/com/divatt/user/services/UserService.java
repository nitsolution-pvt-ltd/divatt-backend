package com.divatt.user.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.json.simple.JSONObject;
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

import com.divatt.user.entity.ProductEntity;
import com.divatt.user.entity.UserDesignerEntity;
import com.divatt.user.entity.PCommentEntity.ProductCommentEntity;
import com.divatt.user.entity.cart.UserCartEntity;
import com.divatt.user.entity.wishlist.WishlistEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repo.UserDesignerRepo;
import com.divatt.user.repo.cart.UserCartRepo;
import com.divatt.user.repo.pCommentRepo.ProductCommentRepo;
import com.divatt.user.repo.wishlist.WishlistRepo;
import com.divatt.user.response.GlobalResponse;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.Body;

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

	public GlobalResponse postWishlistService(WishlistEntity wishlistEntity) {
		LOGGER.info("Inside - UserService.postWishlistService()");

		try {
			Optional<WishlistEntity> findByCategoryName = wishlistRepo
					.findByProductIdAndUserId(wishlistEntity.getProductId(), wishlistEntity.getUserId());
			if (findByCategoryName.isPresent()) {
				throw new CustomException("Product already exist!");
			} else {
				WishlistEntity filterCatDetails = new WishlistEntity();

				filterCatDetails.setId(sequenceGenerator.getNextSequence(wishlistEntity.SEQUENCE_NAME));
				filterCatDetails.setUserId(wishlistEntity.getUserId());
				filterCatDetails.setProductId(wishlistEntity.getProductId());
				filterCatDetails.setAddedOn(new Date());

				wishlistRepo.save(filterCatDetails);
				return new GlobalResponse("SUCCESS", "Wishlist added succesfully", 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse deleteWishlistService(Integer Id) {
		try {
			Optional<WishlistEntity> findByProductRow = wishlistRepo.findById(Id);
			if (!findByProductRow.isPresent()) {
				throw new CustomException("Product not exist!");
			} else {
				wishlistRepo.deleteById(Id);
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

	public ResponseEntity<?> getUserWishlistDetails(@RequestBody JSONObject getWishlist, Integer userId)
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
			wishlistObj.addProperty("limit", Integer.parseInt(getWishlist.get("limit").toString()));
			wishlistObj.addProperty("page", Integer.parseInt(getWishlist.get("page").toString()));
			HttpResponse<JsonNode> response = null;
			if (productIds != null) {
				Unirest.setTimeouts(0, 0);
				response = Unirest.post("http://localhost:8083/dev/product/getProductList")
						.header("Content-Type", "application/json").body(wishlistObj.toString()).asJson();
			}
			return ResponseEntity.ok(new Json(response.getBody().toString()));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse postCartDetailsService(UserCartEntity userCartEntity) {
		LOGGER.info("Inside - UserService.postCartDetailsService()");

		try {
			Optional<UserCartEntity> findByCat = userCartRepo.findByProductIdAndUserId(userCartEntity.getProductId(),
					userCartEntity.getUserId());
			if (findByCat.isPresent()) {
				throw new CustomException("Product already added to the cart.");
			} else {
				UserCartEntity filterCatDetails = new UserCartEntity();

				filterCatDetails.setId(sequenceGenerator.getNextSequence(userCartEntity.SEQUENCE_NAME));
				filterCatDetails.setUserId(userCartEntity.getUserId());
				filterCatDetails.setProductId(userCartEntity.getProductId());
				filterCatDetails.setAddedOn(new Date());

				userCartRepo.save(filterCatDetails);
				return new GlobalResponse("SUCCESS", "Cart added succesfully", 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse deleteCartService(Integer Id) {
		LOGGER.info("Inside - UserService.deleteCartService()");
		try {
			Optional<UserCartEntity> findByProductRow = userCartRepo.findById(Id);
			if (!findByProductRow.isPresent()) {
				throw new CustomException("Cart not exist!");
			} else {
				userCartRepo.deleteById(Id);
				return new GlobalResponse("SUCCESS", "Cart removed succesfully", 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> getUserCartDetailsService(@RequestBody JSONObject getCart, Integer userId)
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
			cartObj.addProperty("limit", Integer.parseInt(getCart.get("limit").toString()));
			cartObj.addProperty("page", Integer.parseInt(getCart.get("page").toString()));

			if (productIds.isEmpty()) {
				return ResponseEntity
						.ok(new Json("{\"reason\": \"ERROR\", \"message\": \"Product not found!\",\"status\": 400}"));
			}

			try {
				Unirest.setTimeouts(0, 0);
				HttpResponse<JsonNode> response = Unirest.post("http://localhost:8083/dev/product/getProductList")
						.header("Content-Type", "application/json").body(cartObj.toString()).asJson();
				return ResponseEntity.ok(new Json(response.getBody().toString()));
			} catch (Exception e2) {
				return ResponseEntity.ok(new Json(
						"{\"reason\": \"ERROR\", \"message\": \"Designer Service is not running!\",\"status\": 400}"));
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

				RowsDetails.setId(sequenceGenerator.getNextSequence(productCommentEntity.SEQUENCE_NAME));
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

	@SuppressWarnings("unchecked")
	public ResponseEntity<?> getProductUser() {
		try {

			RestTemplate restTemplate = new RestTemplate();

			String body = restTemplate.getForEntity("http://localhost:8083/dev/product/userProductList", String.class)
					.getBody();

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

	public Map<String, Object> getDesignerDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, Optional<String> sortBy) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<?> categoryResponse = restTemplate
					.getForEntity("http://localhost:8083/dev/product/userProductList/" + limit, String.class);
			return (Map<String, Object>) categoryResponse.getBody();

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

}
