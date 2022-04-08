package com.divatt.user.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.category.response.GlobalResponse;
import com.divatt.user.entity.wishlist.WishlistEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repository.wishlist.WishlistRepo;
import com.divatt.user.services.WishlistService;


@CrossOrigin
@RestController
@RequestMapping("/wishlist")
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
	
	@DeleteMapping("/delete")
	public GlobalResponse deleteWishlistDetails(@RequestBody WishlistEntity wishlistEntity) {
		LOGGER.info("Inside - WishlistController.deleteWishlistDetails()");

		try {
			 return this.wishlistService.deleteWishlistService(wishlistEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

}
