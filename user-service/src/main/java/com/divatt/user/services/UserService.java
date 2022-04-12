package com.divatt.user.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.divatt.category.response.GlobalResponse;
import com.divatt.user.entity.wishlist.WishlistEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repository.wishlist.WishlistRepo;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import springfox.documentation.spring.web.json.Json;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private WishlistRepo wishlistRepo;

	@Autowired
	SequenceGenerator sequenceGenerator;

	public GlobalResponse postWishlistService(WishlistEntity wishlistEntity) {
		LOGGER.info("Inside - WishlistService.postWishlistService()");

		try {
			Optional<WishlistEntity> findByCategoryName = wishlistRepo
					.findByProductIdAndUserId(wishlistEntity.getProductId(), wishlistEntity.getUserId());
			if (findByCategoryName.isPresent()) {
				return new GlobalResponse("ERROR", "Product Already Exists!", 200);
			} else {
				WishlistEntity filterCatDetails = new WishlistEntity();

				filterCatDetails.setId(sequenceGenerator.getNextSequence(wishlistEntity.SEQUENCE_NAME));
				filterCatDetails.setUserId(wishlistEntity.getUserId());
				filterCatDetails.setProductId(wishlistEntity.getProductId());
				filterCatDetails.setAddedOn(new Date());

				wishlistRepo.save(filterCatDetails);
				return new GlobalResponse("SUCCESS", "Wishlist Added Succesfully", 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public GlobalResponse deleteWishlistService(Integer Id) {
		try {
			Optional<WishlistEntity> findByProductRow = wishlistRepo.findById(Id);
			if (!findByProductRow.isPresent()) {
				return new GlobalResponse("ERROR", "Product Not Exists!", 200);
			} else {
				wishlistRepo.deleteById(Id);
				return new GlobalResponse("SUCCESS", "Wishlist Reomved Succesfully", 200);
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	public Map<String, Object> getWishlistDetails(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy) {
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
				throw new CustomException("Wishlist Not Found!");
			} else {
				return response;
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ResponseEntity<?> getUserWishlistDetails(@RequestBody JSONObject getWishlist, Integer userId)
			throws UnirestException {

		try {

			List<WishlistEntity> findByUserId = wishlistRepo.findByUserId(userId);
			List<Integer> productIds = new ArrayList<>();

			findByUserId.forEach((e) -> {
				productIds.add(e.getProductId());
			});
			LOGGER.info("Inside - WishlistController.postWishlistDetails(usr)" + userId + "ppp" + productIds.toString());
			JsonObject wishlistObj = new JsonObject();
			wishlistObj.addProperty("productId", productIds.toString());
			wishlistObj.addProperty("limit", Integer.parseInt(getWishlist.get("limit").toString()));
			wishlistObj.addProperty("page", Integer.parseInt(getWishlist.get("page").toString()));

			HttpHeaders headers = new HttpHeaders();
//			headers.set("Authorization", token);
//			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity request = new HttpEntity(headers);
			Unirest.setTimeouts(0, 0);
			HttpResponse<JsonNode> response = Unirest.post("http://192.168.29.42:8083/dev/product/getProductList")
					.header("Content-Type", "application/json").body(wishlistObj.toString()).asJson();
			return ResponseEntity.ok(new Json(response.getBody().toString()));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}
