package com.divatt.user.services;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divatt.category.response.GlobalResponse;
import com.divatt.user.entity.wishlist.WishlistEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repository.wishlist.WishlistRepo;


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
				filterCatDetails.setAddedOn(new Date());
						
			
				wishlistRepo.save(filterCatDetails);				
				return new GlobalResponse("SUCCESS", "Wishlist Added Succesfully", 200);				
			}
			
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}


	public GlobalResponse deleteWishlistService(@Valid WishlistEntity wishlistEntity) {
		try {
			Optional<WishlistEntity> findByProductRow = wishlistRepo.findById(wishlistEntity.getId());
			if (!findByProductRow.isPresent()) {
				return new GlobalResponse("ERROR", "Product Not Exists!", 200);
			} else {							
				wishlistRepo.deleteById(wishlistEntity.getId());				
				return new GlobalResponse("SUCCESS", "Wishlist Reomved Succesfully", 200);				
			}
			
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}

}
