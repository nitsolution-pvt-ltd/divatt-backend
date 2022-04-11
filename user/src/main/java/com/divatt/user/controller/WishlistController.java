package com.divatt.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.category.response.GlobalResponse;

import com.divatt.user.entity.UserDesignerEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repository.UserDesignerRepo;
import com.divatt.user.entity.wishlist.WishlistEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repository.wishlist.WishlistRepo;
import com.divatt.user.services.SequenceGenerator;
import com.divatt.user.services.WishlistService;


@CrossOrigin
@RestController
@RequestMapping("/user")
public class WishlistController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WishlistController.class);
	
	
	
	@Autowired WishlistRepo wishlistRepo;
	
	@Autowired WishlistService wishlistService;
	
	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	@Autowired
	private UserDesignerRepo userDesignerRepo;
	
	@PostMapping("/wishlist/add")
	public GlobalResponse postWishlistDetails(@Valid @RequestBody WishlistEntity wishlistEntity) {
		LOGGER.info("Inside - WishlistController.postWishlistDetails()");

		try {
			 return this.wishlistService.postWishlistService(wishlistEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@DeleteMapping("/wishlist/delete")
	public GlobalResponse deleteWishlistDetails(@RequestBody WishlistEntity wishlistEntity) {
		LOGGER.info("Inside - WishlistController.deleteWishlistDetails()");

		try {
			 return this.wishlistService.deleteWishlistService(wishlistEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@PostMapping("/follow")
	public ResponseEntity<?> followDesigner(@Valid @RequestBody UserDesignerEntity userDesignerEntity){
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			userDesignerRepo.findByUserId(userDesignerEntity.getUserId()).ifPresentOrElse((e)->{
				userDesignerEntity.setId(e.getId());
			}, ()->{
				userDesignerEntity.setId(sequenceGenerator.getNextSequence(UserDesignerEntity.SEQUENCE_NAME));
			});
			
			userDesignerEntity.setCreatedOn(date.toString());
			UserDesignerEntity save = userDesignerRepo.save(userDesignerEntity);
			if(save!=null)
				return ResponseEntity.ok(new GlobalResponse("SUCCESS","Data Save Successfully",200));
			else
				throw new CustomException("Data Not Save Try Again");
			
		}catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
