package com.divatt.user.controller;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.user.entity.UserAddressEntity;
import com.divatt.user.entity.UserDesignerEntity;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.PCommentEntity.ProductCommentEntity;
import com.divatt.user.entity.cart.UserCartEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.helper.JwtUtil;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.UserAddressRepo;
import com.divatt.user.repo.UserDesignerRepo;
import com.divatt.user.repo.UserLoginRepo;
import com.divatt.user.entity.wishlist.WishlistEntity;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.SequenceGenerator;
import com.divatt.user.services.UserService;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private OrderDetailsRepo orderDetailsRepo;
	
	@Autowired
	private UserAddressRepo userAddressRepo;
	
	@Autowired
	private Environment env;

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
	
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/wishlist/add")
	public GlobalResponse postWishlistDetails(@Valid @RequestBody ArrayList<WishlistEntity> wishlistEntity) {
		LOGGER.info("Inside - UserController.postWishlistDetails()");

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
		LOGGER.info("Inside - UserController.getWishlistDetails()");

		try {
			return this.userService.getWishlistDetails(page, limit, sort, sortName, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@DeleteMapping("/wishlist/delete")
	public GlobalResponse deleteWishlistDetails(@RequestBody WishlistEntity wishlistEntity) {
		LOGGER.info("Inside - UserController.deleteWishlistDetails()");

		try {
			return this.userService.deleteWishlistService(wishlistEntity.getProductId(),wishlistEntity.getUserId());
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/wishlist/getUserWishist")
	public ResponseEntity<?> getWishlistRestDetails(
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "") Integer userId) {
	
		LOGGER.info("Inside - UserController.getWishlistRestDetails()");

		try {			
			return this.userService.getUserWishlistDetails(userId,page,limit);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/followDesigner")
	public ResponseEntity<?> followDesigner(@Valid @RequestBody UserDesignerEntity userDesignerEntity) {
		try {
			return this.userService.postfollowDesignerService(userDesignerEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/add")
	public ResponseEntity<?> addUser(@Valid @RequestBody UserLoginEntity userLoginEntity, Errors error) {
		LOGGER.info("Inside - UserController.addUser()");
		try {
			if (error.hasErrors()) {
				throw new CustomException("Please check input fields");
			}
			Unirest.setTimeouts(0, 0);
			JsonNode body = Unirest.get("http://localhost:8080/dev/auth/Present/"+userLoginEntity.getEmail())
			  .asJson().getBody();
			JSONObject jsObj = body.getObject();
			if((boolean) jsObj.get("isPresent"))
				throw new CustomException("Email already present");
			if (userLoginRepo.findByEmail(userLoginEntity.getEmail()).isPresent())
				throw new CustomException("Email id is already Present");
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			userLoginEntity.setId((long) sequenceGenerator.getNextSequence(UserLoginEntity.SEQUENCE_NAME));
			userLoginEntity.setUsername(userLoginEntity.getEmail());
			userLoginEntity.setPassword(passwordEncoder.encode(userLoginEntity.getPassword()));
			userLoginEntity.setIsActive(false);
			userLoginEntity.setIsDeleted(false);
			userLoginEntity.setCreatedOn(date.toString());
			userLoginEntity.setProfilePic("");
			userLoginEntity.setRegisterType("Self");
			userLoginRepo.save(userLoginEntity);
			JsonObject jo = new JsonObject();
			jo.addProperty("senderMailId", userLoginEntity.getEmail());
			jo.addProperty("subject", "Successfully Registration");
			jo.addProperty("body", "Welcome " + userLoginEntity.getEmail() + "" + ",\n   "
					+ " you have been register successfully."
					+ "Please active your account by clicking the bellow link "
					+ URI.create(env.getProperty("redirectapi")
							+ Base64.getEncoder().encodeToString(userLoginEntity.getEmail().toString().getBytes()))
					+ " . We will verify your details and come back to you soon.");
			jo.addProperty("enableHtml", false);
			try {
				Unirest.setTimeouts(0, 0);
				Unirest.post("http://localhost:8080/dev/auth/sendMail")
						.header("Content-Type", "application/json").body(jo.toString()).asString();
			} catch (Exception e) {

			}
			return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Registered successfully", 200));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/cart/add")
	public GlobalResponse postCartDetails(@Valid @RequestBody List<UserCartEntity> userCartEntity) {
		LOGGER.info("Inside - UserController.postCartDetails()");

		try {
			return this.userService.postCartDetailsService(userCartEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@PutMapping("/cart/update")
	public ResponseEntity<?> putCartDetails(@Valid @RequestBody UserCartEntity userCartEntity) {
		LOGGER.info("Inside - UserController.putCartDetails()");

		try {
			return this.userService.putCartDetailsService(userCartEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@DeleteMapping("/cart/delete")
	public GlobalResponse deleteCartDetails(@RequestBody UserCartEntity userCartEntity) {
		LOGGER.info("Inside - UserController.deleteCartDetails()");

		try {
			return this.userService.deleteCartService(userCartEntity.getId());
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = { "/cart/getUserCart" }, method = RequestMethod.GET)
	public ResponseEntity<?> getUserCartRestDetails(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@RequestParam(defaultValue = "") Integer userId) {
		LOGGER.info("Inside - UserController.getUserCartRestDetails()");

		try {
			return this.userService.getUserCartDetailsService(userId,page,limit);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/productComment/add")
	public GlobalResponse postProductCommentDetails(@Valid @RequestBody ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserController.postProductCommentDetails()");

		try {
			return this.userService.postProductCommentService(productCommentEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/productComment/update")
	public GlobalResponse putProductCommentDetails(@Valid @RequestBody ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserController.putProductCommentDetails()");

		try {
			return this.userService.putProductCommentService(productCommentEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/productComment/status")
	public GlobalResponse putProductCommentStatusDetails(@RequestBody ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserController.putProductCommentStatusDetails()");

		try {
			return this.userService.putProductCommentStatusService(productCommentEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@DeleteMapping("/productComment/delete")
	public GlobalResponse deleteProductCommentDetails(@RequestBody ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserController.deleteProductCommentDetails()");

		try {
			return this.userService.deleteProductCommentService(productCommentEntity.getId());
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = "/redirect/{email}", method = RequestMethod.GET)
	public void method(HttpServletResponse httpServletResponse, @PathVariable("email") String email) {

		Optional<UserLoginEntity> findByEmail = userLoginRepo
				.findByEmail(new String(Base64.getDecoder().decode(email)));
		if (findByEmail.isPresent()) {
			UserLoginEntity designerLoginEntity = findByEmail.get();
			designerLoginEntity.setIsActive(true);
			userLoginRepo.save(designerLoginEntity);
		}
		httpServletResponse.setHeader("Location", env.getProperty("redirecturl"));
		httpServletResponse.setStatus(302);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserLoginEntity userLoginEntityParam, Errors error) {
		LOGGER.info("Inside - UserController.addUser()");
		try {
			if (error.hasErrors()) {
				throw new CustomException("Please check all input fields");
			}

			Optional<UserLoginEntity> findById = userLoginRepo.findById(userLoginEntityParam.getId());

			if (!findById.isPresent())
				throw new CustomException("User not found");
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			UserLoginEntity userLoginEntity = findById.get();
			userLoginEntity.setFirstName(userLoginEntityParam.getFirstName());
			userLoginEntity.setLastName(userLoginEntityParam.getLastName());
			userLoginEntity.setMobileNo(userLoginEntityParam.getMobileNo());
			userLoginEntity.setDob(userLoginEntityParam.getDob());
			userLoginRepo.save(userLoginEntity);
			return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Updated successfully", 200));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/product/list")
	public ResponseEntity<?> productListing() {
		try {
			return this.userService.getProductUser();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/designer/list")
	public ResponseEntity<?> designerListing() {
		try {
			return this.userService.getDesignerUser();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@RequestMapping(value = { "/getDesignerProductList" }, method = RequestMethod.GET)
	public ResponseEntity<?> getDesignerProductDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - UserController.getDesignerProductDetails()");

		try {
			return this.userService.getDesignerDetails(page, limit, sort, sortName, isDeleted, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/view/{productId}")
	public ResponseEntity<?> viewProductDetails(@PathVariable Integer productId,@RequestParam(defaultValue = "") String userId) {
		LOGGER.info("Inside- UserController.viewProductDetails()");
		try {
			return userService.productDetails(productId,userId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/designerProfile/{designerId}")
	public ResponseEntity<?> viewDesignerProfileDetails(@PathVariable Integer designerId,@RequestHeader(name = "Authorization" , defaultValue = "Bearer ") String token) {
		LOGGER.info("Inside- UserController.viewDesignerProfileDetails()");
		try {
			Long userId = 0l;
			try {
				Optional<UserLoginEntity> findByEmail = userLoginRepo.findByEmail(jwtUtil.extractUsername(token.substring(7)));
				if(!findByEmail.isPresent())
					throw new CustomException("User not valid");
				userId = findByEmail.get().getId();
			}catch(Exception e) {
				
			}
				
			
			
			return userService.getDesignerProfileDetailsService(designerId, userId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getPerDesignerProductList/{designerId}")
	public ResponseEntity<?> getPerDesignerProductDetails(@PathVariable Integer designerId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "DESC") String sort, @RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - UserController.getPerDesignerProductDetails()");

		try {
			return this.userService.getPerDesignerProductListService(page, limit, sort, sortName, isDeleted, keyword,
					sortBy, designerId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/address")
	public ResponseEntity<?> getAllAddress(@RequestHeader(name = "Authorization") String token){
		LOGGER.info("Inside - UserController.getAllAddress()");
		try {
			Optional<UserLoginEntity> findByEmail = userLoginRepo.findByEmail(jwtUtil.extractUsername(token.substring(7)));
			List<UserAddressEntity> findByUserId = userAddressRepo.findByUserId(findByEmail.get().getId());
			if(findByUserId.size()<1)
				throw new CustomException("No address added");
			return ResponseEntity.ok(findByUserId);
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
		
	}
	
	@PostMapping("/address")
	public ResponseEntity<?> addAddress(@Valid @RequestBody() UserAddressEntity userAddressEntity){
		LOGGER.info("Inside - UserController.addAddress()");
		try {
			List<UserAddressEntity> findByUserId = userAddressRepo.findByUserId(userAddressEntity.getUserId());
			if(findByUserId.size()==0) {
				userAddressEntity.setPrimary(true);
			}else {
				if(userAddressEntity.getPrimary()) {
					List<UserAddressEntity> list = findByUserId.stream().map(e->{
						e.setPrimary(false);
						return e;
					}).collect(Collectors.toList());
					userAddressRepo.saveAll(list);
				}
			}
			userAddressEntity.setId((long)sequenceGenerator.getNextSequence(UserAddressEntity.SEQUENCE_NAME));
			userAddressEntity.setCreatedOn(new Date().toString());
			userAddressRepo.save(userAddressEntity);
			return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Address added successfully", 200));
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@PutMapping("/address/{id}")
	public ResponseEntity<?> updateAddress(@Valid @RequestBody() UserAddressEntity userAddressEntity, @PathVariable("id") Long id){
		LOGGER.info("Inside - UserController.updateAddress()");
		try {
			Optional<UserAddressEntity> findById = userAddressRepo.findById(id);
			List<UserAddressEntity> findByUserId = userAddressRepo.findByUserId(userAddressEntity.getUserId());
			if(!findById.isPresent())
				throw new CustomException("Id not found");
			if(findById.get().getPrimary())
				userAddressEntity.setPrimary(true);
			userAddressEntity.setId(findById.get().getId());
			userAddressEntity.setCreatedOn(findById.get().getCreatedOn());
			userAddressRepo.save(userAddressEntity);
			
			
			if(userAddressEntity.getPrimary() && !findById.get().getPrimary()) {
				List<UserAddressEntity> list = findByUserId.stream().map(e->{
					if(e.getId()!=id)
						e.setPrimary(false);
					return e;
				}).collect(Collectors.toList());
				userAddressRepo.saveAll(list);
			}
			return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Address updated successfully", 200));
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	@PutMapping("/address/setprimary/{id}")
	public ResponseEntity<?> setPrimaryAddress(@PathVariable("id") Long id){
		Optional<UserAddressEntity> findById = userAddressRepo.findById(id);
		if(!findById.isPresent())
			throw new CustomException("Id not found");
		List<UserAddressEntity> findByUserId = userAddressRepo.findByUserId(findById.get().getUserId());
		List<UserAddressEntity> list = findByUserId.stream().map(e->{
			if(e.getId()==id)
				e.setPrimary(true);
			else
				e.setPrimary(false);
			return e;
		}).toList();
		userAddressRepo.saveAll(list);
		return ResponseEntity.ok(new GlobalResponse("SUCCESS", "This address has been set as primary", 200));
	}
	
	
	

}
