package com.divatt.user.controller;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
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
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import com.divatt.user.constant.MessageConstant;
import com.divatt.user.constant.RestTemplateConstants;
import com.divatt.user.entity.ProductCommentEntity;
import com.divatt.user.entity.StateEntity;
import com.divatt.user.entity.UserAddressEntity;
import com.divatt.user.entity.UserCartEntity;
import com.divatt.user.entity.UserDesignerEntity;
import com.divatt.user.entity.UserLoginEntity;
import com.divatt.user.entity.WishlistEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.helper.JwtUtil;
import com.divatt.user.repo.UserAddressRepo;
import com.divatt.user.repo.UserLoginRepo;
import com.divatt.user.repo.WishlistRepo;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.SequenceGenerator;
import com.divatt.user.services.UserService;
import com.divatt.user.utill.EmailSenderThread;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private WishlistRepo wishlistRepo;

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
	private UserLoginRepo userLoginRepo;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private TemplateEngine templateEngine;

	@Value("${mail.from}")
	private String mail;
	
	@Value("${rootURL}")
	private String rootURL;
	
	@Value("${DESIGNER}")
	private String DESIGNER_SERVICE;

	@Value("${AUTH}")
	private String AUTH_SERVICE;

	@Value("${ADMIN}")
	private String ADMIN_SERVICE;

	@Value("${USERS}")
	private String USER_SERVICE;
	
	@Value("${redirectURL}")
	private String redirectURL;

	@PostMapping("/wishlist/add")
	public GlobalResponse postWishlistDetails(@Valid @RequestBody ArrayList<WishlistEntity> wishlistEntity) {
		LOGGER.info("Inside - UserController.postWishlistDetails()");

		try {
			return userService.postWishlistService(wishlistEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@SuppressWarnings("all")
	@Scheduled(cron = "0 10 22 * * *") // this method will call at Twice per Day at 10AM & 10PM....
	@GetMapping("/notification")
	public void sendNotification() {

		LOGGER.info("Inside - UserService.sendNotification()");

		try {
			List<WishlistEntity> findByAddedOn = wishlistRepo.findAll();

			for (WishlistEntity e : findByAddedOn) {

				Date date = new Date();
				Calendar calObjOfCurDate = Calendar.getInstance();
				calObjOfCurDate.setTime(date);

				Calendar calObj = Calendar.getInstance();
				calObj.setTime(e.getAddedOn());

				int addedOnInt = calObj.get(Calendar.DATE);
				int calObjOfCurDateInt = calObjOfCurDate.get(Calendar.DATE);

				if (calObjOfCurDateInt % addedOnInt == 7) {
					
					Optional<UserLoginEntity> findById = userLoginRepo.findById((long) e.getUserId());
					try {
						ResponseEntity<String> forEntity = restTemplate.getForEntity(DESIGNER_SERVICE+
								RestTemplateConstants.DESIGNER_PRODUCT_VIEW + e.getProductId(), String.class);

						ObjectMapper objectMapper = new ObjectMapper();
						Map<String, Object> map = objectMapper.readValue(forEntity.getBody(), Map.class);
						JSONObject js = new JSONObject(forEntity.getBody());
						JSONObject jsonObjectOfPrise = new JSONObject(
								new JSONObject(new JSONObject(js.get("price").toString()).toString()).get("indPrice").toString());

						JSONArray jsonArrayOfImage = new JSONArray(js.get("images").toString());
						JSONObject jsonObjectOfImage = new JSONObject(jsonArrayOfImage.get(0).toString());

						JSONArray jsonArrayOfSOH = new JSONArray(js.get("standeredSOH").toString());
						JSONObject jsonObjectOfSOH = new JSONObject(jsonArrayOfSOH.get(0).toString());

						String html = "<tr><td style='width: 10%;text-align: center;padding: 10px;padding-top: 2px;border-left: 1.6px solid #656262;border-bottom: 1.6px solid #656262;'><img src='"
								+ jsonObjectOfImage.get("name")
								+ "' style='width:80%;margin: auto;'></td><td colspan='3' style='width:53%;margin-left:10px;text-align: left;padding: 10px;border-bottom: 1.6px solid #656262;border-left: 1.6px solid #656262;color: #000;'>"
								+ map.get("productDescription")
								+ "</td><td style='width:15%;padding: 5px;\\text-align: center;border: 1.6px solid #656262;'><table style='width:100%;'><tr><td style='width:100%;color: #000;'>Size :</td><td style='color: #000;'>"
								+ jsonObjectOfSOH.get("sizeType")
								+ "</td></tr></table></td><td style='width:16%; ;font-weight: bold;font-size: 14px;border-bottom: 1.6px solid #656262;'><table style='width:100%;'><tr><td style='text-align:center;font-weight: 600;color: #000;'>Rs "
								+ jsonObjectOfPrise.get("mrp") + "</td></tr></table></td></tr>";

						sendEmail(findById.get().getEmail(), MessageConstant.REMINDER_MAIL.getMessage(),
								"<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html lang='en'><head><link rel='preconnect' href='https://fonts.googleapis.com'><link rel='preconnect' href='https://fonts.gstatic.com' crossorigin=''><link href='https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,100;0,400;0,700;0,900;1,100&display=swap' rel='stylesheet'><link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css' integrity='sha512-5A8nwdMOWrSz20fDsjczgUidUBR8liPYU+WymTZP1lmY9G6Oc7HlZv156XqnsgNUzTyMefFTcsFH/tnJE/+xBg==' crossorigin='anonymous' referrerpolicy='no-referrer'></head><body><div style='width: 600px;margin: auto;padding: 15px !important;'><table style='width: 100%;'><thead><tr style='font-size: 20px;'><td colspan='5' style='padding:20px 20px;text-align: center;'><img src='https://mcusercontent.com/4ca4564f8cab8a58cbc0f32e2/images/89ed60f8-9220-1a42-6d9c-92b125b34860.png' alt='' style='width: 100px;'></td></tr></thead><tbody><tr style='font-size: 20px;'><td colspan='5' style='padding:20px 20px;text-align: center;'><div style='font-family: 'Lato', sans-serif;font-size: 22px;text-transform: uppercase;margin-top: 12px;letter-spacing: 1.6px;font-weight: 600;'>YOUR WISHLIST IS MISSING YOU</div><div style='font-family: 'Lato', sans-serif;font-size: 15px;margin-top: 12px;font-weight: 500;line-height: 30px;color: #5b5b5b; '> Checkout your favaurite items from the before they run out of stock. </div><tr style='font-size: 20px;'><td colspan='5' style='padding:20px 20px;text-align: left;font-family: 'Lato', sans-serif;'>   Hi "
										+ findById.get().getFirstName()
										+ ". <br><div style='font-family: 'Lato', sans-serif;text-align: center;font-size: 15px;margin-top: 12px;font-weight: 500;line-height: 30px;color: #5b5b5b; '> Did you face any issue(s) while trying to place an order? Don't worry we have saved the details you've filled in - you're just a couple of clicks away! Click on the button below to review and confirm the order. </div><hr style='border-left: 0;border-width: 3px; border-bottom: 0;border-color: rgb(0 0 0);margin-top: 38px;'></td></tr></td></tr></tbody></table><h1 style='font-family: 'Lato', sans-serif;text-transform: uppercase;font-size: 17px;font-weight: 600;margin-bottom: 22px;color: #000;'>Your Wishlist details</h1><table style='width:100%; height:auto; background-color:#fff; margin-top:0px; padding:20px; border: 1.6px solid #656262; border-collapse: collapse;' border=''><thead><tr style=' color: #494b4d;font-weight: bold; padding: 5px;font-family: 'Lato', sans-serif;font-size: 15px;text-transform: uppercase;font-weight: 900;'><td colspan='1' style='width: 10%;text-align: center; border: 1.6px solid #656262;padding: 10px;border-left: 1.6px solid #656262;'>PRODUCT</td><td colspan='3' style='text-align: center; border-right: 1.6px solid #656262;border-bottom: 1.6px solid #656262;padding: 10px;border-left: 1.6px solid #656262;'>Descption</td><td style='padding: 10px;text-align:center; border-right: 1.6px solid #656262;border-bottom: 1.6px solid #656262;'>SIZE</td><td colspan='1' style='text-align: center;padding: 10px; border-right: 1.6px solid #656262;border-bottom: 1.6px solid #656262;'>PRICE</td></tr></thead><tbody style='font-family: 'Lato', sans-serif;'>"
										+ html
										+ "</tbody></table><table style='width:100%;margin-top: 50px;margin-bottom: 60px;'><tr><td style='text-align:center;font-weight: 600;color: #000;'><a href='http://65.1.190.195/divatt/wishlist' style='padding: 0.375rem 0.75rem;text-transform: uppercase;font-family: 'Lato', sans-serif;text-decoration: none; font-size: 1rem;cursor: pointer;height: 25px;display: block;width: fit-content; margin: auto;line-height: 1.5; border-radius: 0.25rem;color: rgb(255 255 255) !important;letter-spacing: 0.05em;border: 2px solid rgb(135 192 72) !important; background-image: linear-gradient(30deg, rgb(135 192 72) 50%, rgb(0 0 0 / 0%) 50%);background-size: 1000px; background-repeat: no-repeat;background-position: 0;-webkit-transition: background 300ms ease-in-out;transition: background 300ms ease-in-out;' target='_blank'>Complete your order now</a></td></tr></table>"
										+ "<h1 style='font-family: 'Lato', sans-serif;text-transform: uppercase;font-size: 23px;font-weight: 800; margin-bottom: 22px;margin-top: 40px;letter-spacing: 1.6px;text-align: center;'>Follow US</h1><div style='text-align: center;'><a href='#' style='text-decoration: none;color: #000;text-align: center;margin-right: 10px;'><img src='https://mcusercontent.com/4ca4564f8cab8a58cbc0f32e2/images/3c1d4e2a-f7a7-49d7-5da0-033d43c001a9.png' alt='' style='width: 40px;height:40px;'></a><a href='#' style='text-decoration: none;color: #000;text-align: center;margin-right: 10px;'><img src='https://mcusercontent.com/4ca4564f8cab8a58cbc0f32e2/images/903b697c-e17e-3467-37ec-a2579fce3114.jpg' alt='' style='width: 37px;height:37px;'></a><a href='#' style='text-decoration: none;color: #000;text-align: center;margin-right: 10px;'><img src='https://mcusercontent.com/4ca4564f8cab8a58cbc0f32e2/images/05d98f76-7feb-df56-d2ef-ea254e07e373.png' alt='' style='width: 40px;height:40px;'></a><a href='#' style='text-decoration: none;color: #000;text-align: center;margin-right: 10px;'><img src='https://mcusercontent.com/4ca4564f8cab8a58cbc0f32e2/images/17b7c9d8-a3cc-1eb7-7bc8-6ac6c153d52c.png' alt='' style='width: 42px;height:42px;'></a><a href='#' style='text-decoration: none;color: #000;text-align: center;'><img src='https://mcusercontent.com/4ca4564f8cab8a58cbc0f32e2/images/27dc3b48-f225-b21e-1b25-23e3afd95566.png' alt='' style='width: 39px;height:37px;'></a></div></div><script type='text/javascript' src='/LBKlAJ/lDsEbq/5P/c4gA/oVBSEXBP4/7cErfJDL3S/NCUhTw/fHU5/ZXVTawM'></script></body></html>",
								true);
					} catch (Exception Z) {
						Z.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public void sendEmail(String to, String subject, String body, Boolean enableHtml) {

		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setSubject(subject);
			helper.setFrom(mail);
			helper.setTo(to);
			helper.setText(body, enableHtml);
			mailSender.send(message);
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
			return userService.getWishlistDetails(page, limit, sort, sortName, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@DeleteMapping("/wishlist/delete")
	public GlobalResponse deleteWishlistDetails(@RequestBody WishlistEntity wishlistEntity) {
		LOGGER.info("Inside - UserController.deleteWishlistDetails()");

		try {
			return userService.deleteWishlistService(wishlistEntity.getProductId(), wishlistEntity.getUserId());
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/wishlist/getUserWishist")
	public ResponseEntity<?> getWishlistRestDetails(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "") Integer userId) {

		LOGGER.info("Inside - UserController.getWishlistRestDetails()");

		try {
			return userService.getUserWishlistDetails(userId, page, limit);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/followDesigner")
	public ResponseEntity<?> followDesigner(@Valid @RequestBody UserDesignerEntity userDesignerEntity) {
		try {
			return userService.postfollowDesignerService(userDesignerEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/followerCount/{designerId}")
	public GlobalResponse followerCount(@PathVariable Long designerId) {
		try {
			return userService.getCountFollowers(designerId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/add")
	public ResponseEntity<?> addUser(@Valid @RequestBody UserLoginEntity userLoginEntity, Errors error) {
		LOGGER.info("Inside - UserController.addUser()");
		try {
			if (error.hasErrors()) {
				throw new CustomException(MessageConstant.CHECK_FIELDS.getMessage());
			}

			ResponseEntity<String> response = restTemplate.getForEntity(AUTH_SERVICE+
					RestTemplateConstants.USER_LOGIN_PRESENT + userLoginEntity.getEmail(), String.class);
			JSONObject jsonObject = new JSONObject(response.getBody());
			if ((boolean) jsonObject.get("isPresent") && jsonObject.get("role").equals("USER"))
				throw new CustomException(MessageConstant.EMAIL_ALREADY_PRESENT.getMessage());

			if ((boolean) jsonObject.get("isPresent") && jsonObject.get("role").equals("DESIGNER")) {
				ResponseEntity<String> forEntity2 = restTemplate.getForEntity(AUTH_SERVICE+
						RestTemplateConstants.DESIGNER_DETAILS + userLoginEntity.getEmail(), String.class);
				userLoginEntity.setUserExist(forEntity2.getBody());
			}

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

			userLoginEntity.setSocialId(userLoginEntity.getSocialId());
			userLoginEntity.setSocialType(userLoginEntity.getSocialType());
			userLoginRepo.save(userLoginEntity);

			String firstName = userLoginEntity.getFirstName();
			String lastName = userLoginEntity.getLastName();
			String userName = firstName + " " + lastName;
			String userEmail = userLoginEntity.getEmail();

			URI uri = URI.create(redirectURL
					+ Base64.getEncoder().encodeToString(userLoginEntity.getEmail().toString().getBytes()));
			Context context = new Context();
			context.setVariable("userName", userName);
			context.setVariable("uri", uri);
			String htmlContent = templateEngine.process("userRegistration.html", context);
			EmailSenderThread emailSenderThread = new EmailSenderThread(userEmail, "Successfully Registration", htmlContent,
					true, null, restTemplate,AUTH_SERVICE);
			emailSenderThread.start();

			return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.REGISTERED_SUCESSFULLY.getMessage(), 200));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/cart/add")
	public GlobalResponse postCartDetails(@Valid @RequestBody List<UserCartEntity> userCartEntity) {
		LOGGER.info("Inside - UserController.postCartDetails()");

		try {
			return userService.postCartDetailsService(userCartEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/cart/update")
	public ResponseEntity<?> putCartDetails(@Valid @RequestBody UserCartEntity userCartEntity) {
		LOGGER.info("Inside - UserController.putCartDetails()");

		try {
			return userService.putCartDetailsService(userCartEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@DeleteMapping("/cart/delete")
	public GlobalResponse deleteCartDetails(@RequestBody UserCartEntity userCartEntity) {
		LOGGER.info("Inside - UserController.deleteCartDetails()");

		try {
			return userService.deleteCartService(userCartEntity.getId());
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@DeleteMapping("/cart/multipleDelete/{userId}")
	public GlobalResponse multipleDelete(@PathVariable Integer userId) {
		try {
			return userService.multipleDelete(userId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@RequestMapping(value = { "/cart/getUserCart" }, method = RequestMethod.GET)
	public ResponseEntity<?> getUserCartRestDetails(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "") Integer userId) {
		LOGGER.info("Inside - UserController.getUserCartRestDetails()");

		try {
			return userService.getUserCartDetailsService(userId, page, limit);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/productComment/add")
	public GlobalResponse postProductCommentDetails(@Valid @RequestBody ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserController.postProductCommentDetails()");

		try {
			return userService.postProductCommentService(productCommentEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PutMapping("/productComment/update")
	public GlobalResponse putProductCommentDetails(@Valid @RequestBody ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserController.putProductCommentDetails()");

		try {
			return userService.putProductCommentService(productCommentEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/productComment/status")
	public GlobalResponse putProductCommentStatusDetails(@RequestBody ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserController.putProductCommentStatusDetails()");

		try {
			return userService.putProductCommentStatusService(productCommentEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@DeleteMapping("/productComment/delete")
	public GlobalResponse deleteProductCommentDetails(@RequestBody ProductCommentEntity<?> productCommentEntity) {
		LOGGER.info("Inside - UserController.deleteProductCommentDetails()");

		try {
			return userService.deleteProductCommentService(productCommentEntity.getId());
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
				throw new CustomException(MessageConstant.CHECK_FIELDS.getMessage());
			}

			Optional<UserLoginEntity> findById = userLoginRepo.findById(userLoginEntityParam.getId());

			if (!findById.isPresent())
				throw new CustomException(MessageConstant.USER_NOT_FOUND.getMessage());
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			formatter.format(date);
			UserLoginEntity userLoginEntity = findById.get();
			userLoginEntity.setFirstName(userLoginEntityParam.getFirstName());
			userLoginEntity.setLastName(userLoginEntityParam.getLastName());
			userLoginEntity.setMobileNo(userLoginEntityParam.getMobileNo());
			userLoginEntity.setDob(userLoginEntityParam.getDob());
			
			if (userLoginEntityParam.getProfilePic().isEmpty()) {
				userLoginEntity.setProfilePic(findById.get().getProfilePic());
				userLoginRepo.save(userLoginEntity);
				return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.UPDATED_SUCCESSFULLY.getMessage(), 200));
			}
			
			userLoginEntity.setProfilePic(userLoginEntityParam.getProfilePic());
			userLoginRepo.save(userLoginEntity);
			return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.UPDATED_SUCCESSFULLY.getMessage(), 200));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/product/list")
	public ResponseEntity<?> productListing() {
		try {
			return userService.getProductUser();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/designer/list")
	public ResponseEntity<?> designerListing() {
		try {
			return userService.getDesignerUser();
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
			return userService.getDesignerDetails(page, limit, sort, sortName, isDeleted, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/view/{productId}")
	public ResponseEntity<?> viewProductDetails(@PathVariable Integer productId,
			@RequestParam(defaultValue = "") String userId) {
		LOGGER.info("Inside- UserController.viewProductDetails()");
		try {
			return userService.productDetails(productId, userId);
		}catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/viewAdmin/{productId}")
	public ResponseEntity<?> viewProductDetailsAdmin(@PathVariable Integer productId,
			@RequestParam(defaultValue = "") String userId) {
		LOGGER.info("Inside- UserController.viewProductDetailsAdmin()");
		try {
			return userService.productDetailsAdmin(productId, userId);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/designerProfile/{designerId}")
	public ResponseEntity<?> viewDesignerProfileDetails(@PathVariable Integer designerId,
			@RequestHeader(name = "Authorization", defaultValue = "Bearer ") String token) {
		LOGGER.info("Inside- UserController.viewDesignerProfileDetails()");
		try {
			Long userId = 0l;
			try {
				Optional<UserLoginEntity> findByEmail = userLoginRepo
						.findByEmail(jwtUtil.extractUsername(token.substring(7)));
				if (findByEmail.isPresent())
					userId = findByEmail.get().getId();

			} catch (Exception e) {

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
			return userService.getPerDesignerProductListService(page, limit, sort, sortName, isDeleted, keyword, sortBy,
					designerId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/address")
	public ResponseEntity<?> getAllAddress(@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - UserController.getAllAddress()");
		try {
			Optional<UserLoginEntity> findByEmail = userLoginRepo
					.findByEmail(jwtUtil.extractUsername(token.substring(7)));
			List<UserAddressEntity> findByUserId = userAddressRepo.findByUserId(findByEmail.get().getId());
			if (findByUserId.size() < 1)
				throw new CustomException(MessageConstant.NO_ADDRESS_FOUND.getMessage());
			return ResponseEntity.ok(findByUserId);
		} catch (ExpiredJwtException e) {
			LOGGER.error("Error",e.getLocalizedMessage());
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			LOGGER.error("Exception",e.getLocalizedMessage());
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/address/{id}")
	public ResponseEntity<?> getAddressById(@RequestHeader(name = "Authorization") String token,
			@PathVariable("id") Long id) {
		LOGGER.info("Inside - UserController.getAddressById()");
		try {
			Optional<UserLoginEntity> findByEmail = userLoginRepo
					.findByEmail(jwtUtil.extractUsername(token.substring(7)));
			if (!findByEmail.isPresent())
				throw new CustomException(MessageConstant.USER_NOT_FOUND.getMessage());
			Optional<UserAddressEntity> findById = userAddressRepo.findById(id);
		
			if (!findById.isPresent() || (findById.get().getUserId() != findByEmail.get().getId()))
				throw new CustomException(MessageConstant.NO_ADDRESS_FOUND.getMessage());
			return ResponseEntity.ok(findById.get());
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@DeleteMapping("/address/{id}")
	public ResponseEntity<?> deleteAddressById(@RequestHeader(name = "Authorization") String token,
			@PathVariable("id") Long id) {
		LOGGER.info("Inside - UserController.deleteAddressById()");
		try {
			Optional<UserLoginEntity> findByEmail = userLoginRepo
					.findByEmail(jwtUtil.extractUsername(token.substring(7)));
			if (!findByEmail.isPresent())
				throw new CustomException(MessageConstant.USER_NOT_FOUND.getMessage());
			Optional<UserAddressEntity> findById = userAddressRepo.findById(id);
			if (!findById.isPresent() || (findById.get().getUserId() != findByEmail.get().getId()))
				throw new CustomException(MessageConstant.NO_ADDRESS_FOUND.getMessage());
			if (findById.get().getPrimary())
				throw new CustomException(MessageConstant.PRIMARY_ADDRESS_NOT_DELETED.getMessage());
			userAddressRepo.deleteById(id);

			return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.ADDRESS_DELETED.getMessage(), 200));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/address")
	public ResponseEntity<?> addAddress(@Valid @RequestBody() UserAddressEntity userAddressEntity) {
		LOGGER.info("Inside - UserController.addAddress()");
		try {
			List<UserAddressEntity> findByUserId = userAddressRepo.findByUserId(userAddressEntity.getUserId());
			if (findByUserId.size() == 0) {
				userAddressEntity.setPrimary(true);
			} else {
				if (userAddressEntity.getPrimary()) {
					List<UserAddressEntity> list = findByUserId.stream().map(e -> {
						e.setPrimary(false);
						return e;
					}).collect(Collectors.toList());
					userAddressRepo.saveAll(list);
				}
			}
			userAddressEntity.setId((long) sequenceGenerator.getNextSequence(UserAddressEntity.SEQUENCE_NAME));
			userAddressEntity.setCreatedOn(new Date().toString());
			userAddressRepo.save(userAddressEntity);
			return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.ADDRESS_ADDED.getMessage(), 200));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/address/{id}")
	public ResponseEntity<?> updateAddress(@Valid @RequestBody() UserAddressEntity userAddressEntity,
			@PathVariable("id") Long id) {
		LOGGER.info("Inside - UserController.updateAddress()");
		try {
			Optional<UserAddressEntity> findById = userAddressRepo.findById(id);
			List<UserAddressEntity> findByUserId = userAddressRepo.findByUserId(userAddressEntity.getUserId());
			if (!findById.isPresent())
				throw new CustomException(MessageConstant.ID_NOT_FOUND.getMessage());
			if (findById.get().getPrimary())
				userAddressEntity.setPrimary(true);
			userAddressEntity.setId(findById.get().getId());
			userAddressEntity.setCreatedOn(findById.get().getCreatedOn());
			userAddressRepo.save(userAddressEntity);

			findByUserId = userAddressRepo.findByUserId(userAddressEntity.getUserId());
			if (userAddressEntity.getPrimary()) {

				List<UserAddressEntity> list = findByUserId.stream().map(e -> {
					if (e.getId() != id) {
						System.out.println("e.getId() " + e.getId() + " / id " + id);
						e.setPrimary(false);
					} else {
						System.out.println("---e.getId() " + e.getId() + " / id " + id);
						e.setPrimary(true);
					}

					return e;
				}).collect(Collectors.toList());
				userAddressRepo.saveAll(list);
			}
			return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
					MessageConstant.ADDRESS_UPDATED.getMessage(), 200));
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/address/setprimary/{id}")
	public ResponseEntity<?> setPrimaryAddress(@PathVariable("id") Long id) {
		Optional<UserAddressEntity> findById = userAddressRepo.findById(id);
		if (!findById.isPresent())
			throw new CustomException(MessageConstant.ID_NOT_FOUND.getMessage());
		
		List<UserAddressEntity> findByUserId = userAddressRepo.findByUserId(findById.get().getUserId());
		
		List<UserAddressEntity> list = findByUserId.stream().map(e -> {
			if (e.getId() == id)
				e.setPrimary(true);
			else
				e.setPrimary(false);
			return e;
		}).collect(Collectors.toList());
		
		userAddressRepo.saveAll(list);
		return ResponseEntity.ok(new GlobalResponse(MessageConstant.SUCCESS.getMessage(),
				MessageConstant.ADDRESS_SET_PRIMARY.getMessage(), 200));
	}

	@GetMapping("/viewProductByOrderId/{orderId}")
	public List<Integer> viewproductByOrderId(@PathVariable String orderId) {
		try {
			return userService.viewProductService(orderId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getUserList")
	public Map<String, Object> getPerDesignerProductDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - UserController.getPerDesignerProductDetails()");

		try {
			return userService.getUserListService(page, limit, sort, sortName, isDeleted, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/followedUserList/{designerIdvalue}")
	public List<UserDesignerEntity> followedUserList(@PathVariable Integer designerIdvalue) {
		try {
			return userService.followedUserListService(designerIdvalue);
		} catch (Exception e) {

			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getUserId/{userId}")
	public UserLoginEntity getUserDetail(@PathVariable Long userId) {
		try {
			return userService.getUserById(userId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getUserAddress/{userId}")
	public UserAddressEntity getUserAddressDeatails(@PathVariable Long userId) {
		try {
			return userAddressRepo.findByUserId(userId).get(0);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getUserDetails/{token}")
	public UserLoginEntity getUserLoginEntity(@PathVariable String token) {
		try {
			String extractedToken = token.substring(7);
			LOGGER.info(extractedToken);
			return userService.getUserDetailsService(extractedToken);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/userStatusInformation")
	public Map<String, Object> getUserStatus() {

		try {
			LOGGER.info("Inside - ProductController.getUserStatus()");
			return userService.getUserStatus();
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getStateData")
	public List<StateEntity> getStateData() {
		try {
			LOGGER.info("Inside --> UserController.getStateData");
			return userService.getStateDataService();
		} catch (Exception e) {

			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/followedDesigner/{userEmail}")
	public List<org.json.simple.JSONObject> followedDesigner(@PathVariable String userEmail) {
		try {
			LOGGER.info("inside controller");
			return this.userService.getListDesignerData(userEmail);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/complaintMail/{productId}")
	public String ComplaintMail(@RequestHeader("Authorization") String token, @PathVariable Integer productId) {
		try {
			return this.userService.complaintMail(token, productId);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/find")
	public Map<String, Object> find(@RequestParam String orderItemStatus, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam Optional<String> sortBy) {
		try {
			return this.userService.find(page, limit, sort, orderItemStatus, isDeleted, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/findByorderID/{orderId}")
	public List<OrderSKUDetailsEntity> findByorderID(@PathVariable String orderId) {
		try {
			return this.userService.findByorderID(orderId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("getUserDesignerDetails/{userEmail}")
	public List<UserDesignerEntity> getUserDesignerDetails(@PathVariable String userEmail) {
		try {
			return this.userService.getUserDesignerDetails(userEmail);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}