package com.divatt.userwishlist.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.category.response.GlobalResponse;
import com.divatt.userwishlist.entity.WishlistEntity;
import com.divatt.userwishlist.exception.CustomException;
import com.divatt.userwishlist.repository.WishlistRepo;
import com.divatt.userwishlist.service.WishlistService;

@CrossOrigin
@RestController
@RequestMapping("/user-wishlist")
public class WishlistController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WishlistController.class);
	
	
	
	@Autowired WishlistRepo wishlistRepo;
	
	@Autowired WishlistService wishlistService;
	@PostMapping("/add")
	public GlobalResponse postWishlistDetails(@Valid @RequestBody WishlistEntity wishlistEntity) {
		LOGGER.info("Inside - WishlistController.postWishlistDetails()");

		try {
			 return this.wishlistService.postWishlistService(wishlistEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}
