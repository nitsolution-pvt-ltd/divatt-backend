package com.divatt.user.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.category.response.GlobalResponse;

import com.divatt.user.entity.UserDesignerEntity;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repository.UserDesignerRepo;
import com.divatt.user.repository.UserLoginRepo;
import com.divatt.user.entity.wishlist.WishlistEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repository.wishlist.WishlistRepo;
import com.divatt.user.services.SequenceGenerator;
import com.divatt.user.services.UserService;
import com.google.gson.JsonObject;
import com.mashape.unirest.request.body.Body;

import springfox.documentation.spring.web.json.Json;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private WishlistRepo wishlistRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private UserDesignerRepo userDesignerRepo;
	
	@Autowired
	private UserLoginRepo userLoginRepo;

	@PostMapping("/wishlist/add")
	public GlobalResponse postWishlistDetails(@Valid @RequestBody WishlistEntity wishlistEntity) {
		LOGGER.info("Inside - WishlistController.postWishlistDetails()");

		try {
			return this.userService.postWishlistService(wishlistEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = { "/wishlist/list" }, method = RequestMethod.GET)
	public Map<String, Object> getWishlistDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - WishlistController.getWishlistDetails()");

		try {
			return this.userService.getWishlistDetails(page, limit, sort, sortName, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@DeleteMapping("/wishlist/delete")
	public GlobalResponse deleteWishlistDetails(@RequestBody WishlistEntity wishlistEntity) {
		LOGGER.info("Inside - WishlistController.deleteWishlistDetails()");

		try {
			return this.userService.deleteWishlistService(wishlistEntity.getId());
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = { "/wishlist/getUserWishist" }, method = RequestMethod.GET)
	public ResponseEntity<?> getWishlistRestDetails(@RequestBody org.json.simple.JSONObject getWishlist, @RequestParam(defaultValue = "") Integer userId) {
		LOGGER.info("Inside - WishlistController.getWishlistRestDetails()");

		try {
			return this.userService.getUserWishlistDetails(getWishlist,userId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/follow")
	public ResponseEntity<?> followDesigner(@Valid @RequestBody UserDesignerEntity userDesignerEntity) {
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
			if (save != null)
				return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Data Save Successfully", 200));
			else
				throw new CustomException("Data Not Save Try Again");

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addUser(@Valid @RequestBody UserLoginEntity userLoginEntity,Errors error){
		LOGGER.info("Inside - EcomAuthController.addUser()");
		try {		
			if (error.hasErrors()) {
				throw new CustomException("Check The Fields");
			}
			if(userLoginRepo.findByEmail(userLoginEntity.getEmail()).isPresent())
				throw new CustomException("Email id is already Present");
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			userLoginEntity.setuId((long)sequenceGenerator.getNextSequence(UserLoginEntity.SEQUENCE_NAME));
			userLoginEntity.setUsername(userLoginEntity.getEmail());
			userLoginEntity.setPassword(passwordEncoder.encode(userLoginEntity.getPassword()));
			userLoginEntity.setIsActive(true);
			userLoginEntity.setIsDeleted(false);
			userLoginEntity.setCreatedOn(date.toString());
			userLoginEntity.setProfilePic("Pic.jpg");
			userLoginEntity.setRegisterType("Self");
			userLoginRepo.save(userLoginEntity);
			return ResponseEntity.ok(new GlobalResponse("SUCCESS","Added Successfully",200));
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}

}
