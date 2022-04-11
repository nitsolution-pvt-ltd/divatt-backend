package com.divatt.user.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.category.response.GlobalResponse;
import com.divatt.user.entity.wishlist.WishlistEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repository.wishlist.WishlistRepo;
import com.divatt.user.services.WishlistService;
import com.mashape.unirest.request.body.Body;

@CrossOrigin
@RestController
@RequestMapping("/wishlist")
public class WishlistController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WishlistController.class);

	@Autowired
	WishlistRepo wishlistRepo;

	@Autowired
	WishlistService wishlistService;

	@PostMapping("/add")
	public GlobalResponse postWishlistDetails(@Valid @RequestBody WishlistEntity wishlistEntity) {
		LOGGER.info("Inside - WishlistController.postWishlistDetails()");

		try {
			return this.wishlistService.postWishlistService(wishlistEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@DeleteMapping("/delete")
	public GlobalResponse deleteWishlistDetails(@RequestBody WishlistEntity wishlistEntity) {
		LOGGER.info("Inside - WishlistController.deleteWishlistDetails()");

		try {
			return this.wishlistService.deleteWishlistService(wishlistEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getWishlistDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - WishlistController.getWishlistDetails()");

		try {
			return this.wishlistService.getWishlistDetails(page, limit, sort, sortName, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	
	@RequestMapping(value = { "/list-rest" }, method = RequestMethod.GET)
	public ResponseEntity<?> getWishlistRestDetails(@RequestParam(defaultValue = "") Integer userId) {
		LOGGER.info("Inside - WishlistController.getWishlistRestDetails()");

		try {
			return this.wishlistService.getWishlistRestDetails(userId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	

}
