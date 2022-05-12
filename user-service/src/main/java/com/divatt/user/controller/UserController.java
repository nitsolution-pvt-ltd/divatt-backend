package com.divatt.user.controller;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.user.entity.ProductEntity;
import com.divatt.user.entity.UserDesignerEntity;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.PCommentEntity.ProductCommentEntity;
import com.divatt.user.entity.cart.UserCartEntity;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.UserDesignerRepo;
import com.divatt.user.repo.UserLoginRepo;
import com.divatt.user.entity.wishlist.WishlistEntity;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.SequenceGenerator;
import com.divatt.user.services.UserService;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private OrderDetailsRepo orderDetailsRepo;
	
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
			return this.userService.deleteWishlistService(wishlistEntity.getId());
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
				HttpResponse<String> response = Unirest.post("http://localhost:8080/dev/auth/sendMail")
						.header("Content-Type", "application/json").body(jo.toString()).asString();
			} catch (Exception e) {

			}
			return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Registered successfully", 200));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/cart/add")
	public GlobalResponse postCartDetails(@Valid @RequestBody UserCartEntity userCartEntity) {
		LOGGER.info("Inside - UserController.postCartDetails()");

		try {
			return this.userService.postCartDetailsService(userCartEntity);
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
	public ResponseEntity<?> getUserCartRestDetails(@RequestBody org.json.simple.JSONObject getWishlist,
			@RequestParam(defaultValue = "") Integer userId) {
		LOGGER.info("Inside - UserController.getUserCartRestDetails()");

		try {
			return this.userService.getUserCartDetailsService(getWishlist, userId);
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
	public ResponseEntity<?> viewProductDetails(@PathVariable Integer productId) {
		LOGGER.info("Inside- UserController.viewProductDetails()");
		try {
			return userService.productDetails(productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/designerProfile/{designerId}")
	public ResponseEntity<?> viewDesignerProfileDetails(@PathVariable Integer designerId) {
		LOGGER.info("Inside- UserController.viewDesignerProfileDetails()");
		try {
			return userService.getDesignerProfileDetailsService(designerId);
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


	@PostMapping("/order/payment/add")
	public GlobalResponse postOrderPaymentDetails(@Valid @RequestBody OrderPaymentEntity orderPaymentEntity) {
		LOGGER.info("Inside - UserController.postOrderPaymentDetails()");

		try {
			return this.userService.postOrderPaymentService(orderPaymentEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@RequestMapping(value = { "/order/payment/list" }, method = RequestMethod.GET)
	public Map<String, Object> getOrderPaymentDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
		    @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - UserController.getOrderPaymentDetails()");

		try {
			return this.userService.getOrderPaymentService(page, limit, sort, sortName, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@PostMapping("/order")
	public ResponseEntity<?> addOrder(@RequestBody OrderDetailsEntity orderDetailsEntity){
		LOGGER.info("Inside - UserController.addOrder()");
		try {
			orderDetailsRepo.save(orderDetailsEntity);
			return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Updated successfully", 200));
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	
	
	

}
