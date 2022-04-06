package com.divatt.userwishlist.service;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divatt.category.response.GlobalResponse;
import com.divatt.userwishlist.entity.WishlistEntity;
import com.divatt.userwishlist.exception.CustomException;
import com.divatt.userwishlist.repository.WishlistRepo;

@Service
public class WishlistService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WishlistService.class);
	@Autowired 
	WishlistRepo wishlistRepo;
	
	@Autowired
	SequenceGenerator sequenceGenerator;
	
	
	public GlobalResponse postWishlistService(WishlistEntity wishlistEntity) {
			LOGGER.info("Inside - WishlistService.postWishlistService()");
		
		try {
			Optional<WishlistEntity> findByCategoryName = wishlistRepo.findByProductId(wishlistEntity.getProductId());
			if (findByCategoryName.isPresent()) {
				return new GlobalResponse("ERROR", "Product Already Exists!", 200);
			} else {
				WishlistEntity filterCatDetails = new WishlistEntity();

				
				filterCatDetails.setId(sequenceGenerator.getNextSequence(wishlistEntity.SEQUENCE_NAME));
				filterCatDetails.setUserId(wishlistEntity.getUserId());				
				filterCatDetails.setProductId(wishlistEntity.getProductId());
				filterCatDetails.setAdded_on(new Date());
						
			
				wishlistRepo.save(filterCatDetails);				
				return new GlobalResponse("SUCCESS", "Wishlist Added Succesfully", 200);				
			}
			
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}

}
