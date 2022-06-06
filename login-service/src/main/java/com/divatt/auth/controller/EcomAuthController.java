package com.divatt.auth.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import org.apache.tomcat.jni.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.auth.entity.GlobalEntity;
import com.divatt.auth.entity.GlobalResponse;
import com.divatt.auth.entity.AdminLoginEntity;
import com.divatt.auth.entity.DesignerLoginEntity;
import com.divatt.auth.entity.LoginAdminData;
import com.divatt.auth.entity.LoginDesignerData;
import com.divatt.auth.entity.LoginUserData;
import com.divatt.auth.entity.PasswordResetEntity;
import com.divatt.auth.entity.SendMail;
import com.divatt.auth.entity.UserLoginEntity;
import com.divatt.auth.exception.CustomException;
import com.divatt.auth.helper.JwtUtil;
import com.divatt.auth.repo.AdminLoginRepository;
import com.divatt.auth.repo.DesignerLoginRepo;
import com.divatt.auth.repo.DesignerProfileRepo;
import com.divatt.auth.repo.PasswordResetRepo;
import com.divatt.auth.repo.UserLoginRepo;
import com.divatt.auth.services.LoginUserDetails;
import com.divatt.auth.services.MailService;
import com.divatt.auth.services.SequenceGenerator;
import com.google.gson.JsonObject;

import springfox.documentation.spring.web.json.Json;

@RestController
@SuppressWarnings("all")
@RequestMapping("/auth")

public class EcomAuthController implements EcomAuthContollerMethod {

	@Autowired
	private MailService mailService;

	@Autowired
	private AdminLoginRepository loginRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private DesignerLoginRepo designerLoginRepo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private PasswordResetRepo loginResetRepo;

	@Autowired
	private UserLoginRepo userLoginRepo;

	@Autowired
	private SequenceGenerator sequenceGenerator;

	@Autowired
	private DesignerProfileRepo designerProfileRepo;

	Logger LOGGER = LoggerFactory.getLogger(EcomAuthController.class);

	@PostMapping("/login")
	public ResponseEntity<?> superAdminLogin(@RequestBody AdminLoginEntity loginEntity) {

		LOGGER.info("Inside - EcomAuthController.superAdminLogin()");

		try {
			
		//** CHECKING THE USER IS REAL OR NOT  **//
			try {
				this.authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginEntity.getEmail(), loginEntity.getPassword()));
			} catch (Exception e) {
				if (e.getMessage().equals("Bad credentials"))
					throw new CustomException("Please check your password");
				else
					throw new CustomException(e.getMessage());

			}
		//** CHECKING END(IF USER IS REAL THEN ONLY HE CAN GO TO NEXT LINE)  **//
			
			
			UserDetails vendor = this.loginUserDetails.loadUserByUsername(loginEntity.getEmail());

			String token = jwtUtil.generateToken(vendor);
		//*****  WE ARE CHECKING IN THREE TABLE ADMIN DESIGNER AND USER  *****//
			//** IF IT IS A ADMIN START **//
			
			if(loginEntity.getType().equals("ADMIN")) {
				Optional<AdminLoginEntity> findByUserName = loginRepository.findByEmail(vendor.getUsername());
				if (findByUserName.isPresent()) {  
					if (!findByUserName.get().isActive())
						throw new CustomException("This account has been deactive");
					return ResponseEntity
							.ok(new LoginAdminData(token, findByUserName.get().getUid(), findByUserName.get().getEmail(),
									findByUserName.get().getPassword(), "Login successful", 200, "ADMIN"));
				}
			}
//			Optional<AdminLoginEntity> findByUserName = loginRepository.findByEmail(vendor.getUsername());
//			if (findByUserName.isPresent()) {  
//				if (!findByUserName.get().isActive())
//					throw new CustomException("This account has been deactive");
//				return ResponseEntity
//						.ok(new LoginAdminData(token, findByUserName.get().getUid(), findByUserName.get().getEmail(),
//								findByUserName.get().getPassword(), "Login successful", 200, "ADMIN"));
			
			//**  ADMIN END **//
				
			//** IF IT IS A DESIGNER START **//	
//			} else {
			if(loginEntity.getType().equals("DESIGNER")) {
				Optional<DesignerLoginEntity> findByUserNameDesigner = designerLoginRepo
						.findByEmail(vendor.getUsername());
				if (findByUserNameDesigner.isPresent()) {
					if (findByUserNameDesigner.get().getProfileStatus().equals("INACTIVE"))
						throw new CustomException("Please active your account");
					if (findByUserNameDesigner.get().getProfileStatus().equals("ACTIVE"))
						throw new CustomException("Waiting for admin approve");
					try {
						if (!findByUserNameDesigner.get().getAccountStatus().equals("ACTIVE"))
							throw new CustomException("This account has been deactive");
					} catch (Exception e) {
					}

					DesignerLoginEntity designerLoginEntity = findByUserNameDesigner.get();
					designerLoginEntity.setAuthToken(token);
					designerLoginRepo.save(designerLoginEntity);
					LoginDesignerData loginDesignerData = new LoginDesignerData(findByUserNameDesigner.get().getUid(),
							findByUserNameDesigner.get().getEmail(), findByUserNameDesigner.get().getPassword(),
							"Login successful",
							Stream.of("DESIGNER").map(SimpleGrantedAuthority::new).collect(Collectors.toList()), 200,
							findByUserNameDesigner.get().getAdminComment(),
							findByUserNameDesigner.get().getProfileStatus(), token, "DESIGNER");

					return new ResponseEntity<>(loginDesignerData, HttpStatus.OK);
			}else {
				throw new CustomException("Email not found");
			}
			}
				if(loginEntity.getType().equals("USER")) {
					Optional<UserLoginEntity> findByEmail = userLoginRepo.findByEmail(vendor.getUsername());
					if (findByEmail.isPresent()) {
						if (findByEmail.get().getIsActive() == false)
							throw new CustomException("Please active your account");
						return ResponseEntity.ok(new LoginUserData(token, findByEmail.get().getuId(),
								findByEmail.get().getEmail(), findByEmail.get().getPassword(), "Login Successfully",
								Stream.of("USER").map(SimpleGrantedAuthority::new).collect(Collectors.toList()), 200));
					} else {
						throw new CustomException("Email not found");
					}
				}
				
				throw new CustomException("No data found");
//				Optional<DesignerLoginEntity> findByUserNameDesigner = designerLoginRepo
//						.findByEmail(vendor.getUsername());
//				if (findByUserNameDesigner.isPresent()) {
//					if (findByUserNameDesigner.get().getProfileStatus().equals("INACTIVE"))
//						throw new CustomException("Please active your account");
//					if (findByUserNameDesigner.get().getProfileStatus().equals("ACTIVE"))
//						throw new CustomException("Waiting for admin approve");
//					try {
//						if (!findByUserNameDesigner.get().getAccountStatus().equals("ACTIVE"))
//							throw new CustomException("This account has been deactive");
//					} catch (Exception e) {
//					}
//
//					DesignerLoginEntity designerLoginEntity = findByUserNameDesigner.get();
//					designerLoginEntity.setAuthToken(token);
//					designerLoginRepo.save(designerLoginEntity);
//					LoginDesignerData loginDesignerData = new LoginDesignerData(findByUserNameDesigner.get().getUid(),
//							findByUserNameDesigner.get().getEmail(), findByUserNameDesigner.get().getPassword(),
//							"Login successful",
//							Stream.of("DESIGNER").map(SimpleGrantedAuthority::new).collect(Collectors.toList()), 200,
//							findByUserNameDesigner.get().getAdminComment(),
//							findByUserNameDesigner.get().getProfileStatus(), token, "DESIGNER");
//
//					return new ResponseEntity<>(loginDesignerData, HttpStatus.OK);
					
			//**  DESIGNER END **//
					
			//** IF IT IS A USER START **//	
					
//				} else {
//
//					Optional<UserLoginEntity> findByEmail = userLoginRepo.findByEmail(vendor.getUsername());
//					if (findByEmail.isPresent()) {
//						if (findByEmail.get().getIsActive() == false)
//							throw new CustomException("Please active your account");
//						return ResponseEntity.ok(new LoginUserData(token, findByEmail.get().getuId(),
//								findByEmail.get().getEmail(), findByEmail.get().getPassword(), "Login Successfully",
//								Stream.of("USER").map(SimpleGrantedAuthority::new).collect(Collectors.toList()), 200));
//					} else {
//						throw new CustomException("Email not found");
//					}
//
//				}
			//**  USER END **//

			

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PostMapping("/sendMail")
	@Description(value = "This API is responsible for send the mail")
	public ResponseEntity<String> sendMail(@RequestBody() SendMail senderMailId) {
		LOGGER.info("Inside - EcomAuthController.sendMail()");

		try {
			mailService.sendEmail(senderMailId.getSenderMailId(), senderMailId.getSubject(), senderMailId.getBody(),
					senderMailId.isEnableHtml());
			return new ResponseEntity<>("Mail sent successfully", HttpStatus.OK);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	
	@PostMapping("/sendEmailWithAttachment")
	@Description(value = "This API is responsible for send the mail with attachment")
	public ResponseEntity<String> sendEmailWithAttachment(@RequestBody() SendMail senderMailId) {
		LOGGER.info("Inside - EcomAuthController.sendMail()");

		try {
//			ClassPathResource file = new ClassPathResource("D:\\Daivatt\\code\\divatt-backend\\login-service\\invoice.pdf");
			File file = new File("invoice.pdf");
			mailService.sendEmailWithAttachment(senderMailId.getSenderMailId(), senderMailId.getSubject(), senderMailId.getBody(),
					senderMailId.isEnableHtml(),file);
			return new ResponseEntity<>("Mail sent successfully", HttpStatus.OK);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/mailForgotPasswordLink/{email}")
	@Description("Using This API You Can Send The Recovery Link to Email, and Using That Link User Can Recover The Password")
	public GlobalResponse mailForgotPasswordLink(@PathVariable("email") String email) {

		LOGGER.info("Inside - EcomAuthController.mailForgotPasswordLink()");

		try {

			UUID uuid = UUID.randomUUID();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			Date date = new Date();
			String format = formatter.format(date);
			Calendar calObj = Calendar.getInstance();
			calObj.setTime(date);
//			String forgotPasswordLinkCreateTime = calObj.get(Calendar.YEAR) + "-" + (calObj.get(Calendar.MONTH) + 1)+ "-" + calObj.get(Calendar.DATE) + "-" + (calObj.get(Calendar.HOUR) + "-"+ (calObj.get(Calendar.MINUTE) + "-" + (calObj.get(Calendar.SECOND))));
			String encodedString = Base64.getEncoder().encodeToString(format.getBytes());
			byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
			String decodedString = new String(decodedBytes);
			String forgotPasswordLink = uuid.toString() + "/" + format;

			// ** CHECKING THE EMAIL IS PREASENT IN DATABASE **//
			Optional<AdminLoginEntity> findByUserName = loginRepository.findByEmail(email);
			Optional<DesignerLoginEntity> findByUserNameDesigner = designerLoginRepo.findByEmail(email);
			Optional<UserLoginEntity> findByUserNameUser = userLoginRepo.findByEmail(email);
			if (!findByUserNameDesigner.isPresent() && !findByUserNameUser.isPresent() && !findByUserName.isPresent())
				throw new CustomException("Username not found");
//			if (findByUserName.isPresent()) {
			PasswordResetEntity loginResetEntity = new PasswordResetEntity();
			Object id = null;
			try {
				id = findByUserName.get().getUid();
				loginResetEntity.setUser_type(findByUserName.get().getRole());

			} catch (Exception e) {
				try {
					id = findByUserNameDesigner.get().getUid();
					loginResetEntity.setUser_type("DESIGNER");
				} catch (Exception j) {
					id = findByUserNameUser.get().getuId();
					loginResetEntity.setUser_type("USER");
				}

			}

			loginResetEntity.setUser_id(id);
			loginResetEntity.setPrtoken(uuid.toString() + "/" + format);
			loginResetEntity.setStatus("ACTIVE");
			loginResetEntity.setId(sequenceGenerator.getNextSequence(PasswordResetEntity.SEQUENCE_NAME));

			loginResetEntity.setEmail(email);
			Date dateObjForLinkCreateTime = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss").parse(format);

			loginResetEntity.setCreated_on(dateObjForLinkCreateTime);
			// ** SAVE THE DETAILS IN DATABASE **//
			PasswordResetEntity save = loginResetRepo.save(loginResetEntity);

			if (save.equals(null)) {
				throw new CustomException("Data not save! try again");
			} else {
				// ** SEND MAIL IF DETAILS SAVE IN DATABASE **//
				try {
					mailService.sendEmail(findByUserName.get().getEmail(), "Forgot Password Link",
							"Hi " + findByUserName.get().getFirstName() + " " + findByUserName.get().getLastName()
									+ " This is Your Link Reset Password " + "http://65.1.190.195/admin/#/auth/reset-password/"+forgotPasswordLink,
							false);

				} catch (Exception e) {
					try {
						mailService.sendEmail(findByUserNameDesigner.get().getEmail(), "Forgot Password Link",
								"Hi " + findByUserNameDesigner.get().getEmail()
										+ " This is Link for reset password " + "http://65.1.190.195/admin/#/auth/reset-password/"+forgotPasswordLink,
								false);
					} catch (Exception Z) {
						mailService.sendEmail(findByUserNameUser.get().getEmail(), "Forgot Password Link",
								"Hi " + findByUserNameUser.get().getEmail() + " This is Link for reset password "
										+ "http://65.1.190.195/divatt/#/forgetpassword/"+forgotPasswordLink,
								false);
					}

				}

			}

			return new GlobalResponse("SUCCESS", "Mail sent successfully", 200);

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/resetPassword/{link}/{linkTime}")
	@Description("After Got The Link in Mail, Using That link User Can Create New Password")
	public GlobalResponse resetPassword(@PathVariable("link") String link, @PathVariable("linkTime") String linkTime,
			@RequestBody GlobalEntity globalEntity) {

		LOGGER.info("Inside - EcomAuthController.resetPassword()");
		try {
			Optional<PasswordResetEntity> findByPrToken = loginResetRepo.findByPrtoken(link + "/" + linkTime);

			if (findByPrToken.isPresent()) {
				if (findByPrToken.get().getStatus().equals("ACTIVE")) {

					// ** Create Current Time **//
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
					Date date = new Date();
					formatter.format(date);
					Calendar calObjForCurrentTime = Calendar.getInstance();
					calObjForCurrentTime.setTime(date);

//					byte[] forgotPasswordLinkCreateTimeByte = Base64.getDecoder().decode(linkTime);
//					String forgotPasswordLinkCreateTimeString = new String(forgotPasswordLinkCreateTimeByte);
					Calendar calObjForLinkCreateTime = Calendar.getInstance();
					Date dateObjForLinkCreateTime = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss").parse(linkTime);
					calObjForLinkCreateTime.setTime(dateObjForLinkCreateTime);

					if (calObjForCurrentTime.get(Calendar.YEAR) == calObjForLinkCreateTime.get(Calendar.YEAR)
							&& calObjForCurrentTime.get(Calendar.MONTH) == calObjForLinkCreateTime.get(Calendar.MONTH)
							&& calObjForCurrentTime.get(Calendar.DATE) == calObjForLinkCreateTime.get(Calendar.DATE)
							&& calObjForCurrentTime.get(Calendar.HOUR) == calObjForLinkCreateTime.get(Calendar.HOUR)
							&& calObjForLinkCreateTime.get(Calendar.MINUTE) <= calObjForCurrentTime.get(Calendar.MINUTE)
							&& calObjForCurrentTime.get(Calendar.MINUTE) <= calObjForLinkCreateTime.get(Calendar.MINUTE)
									+ 5) {

					} else {
						throw new CustomException("This link has been expierd");
					}
					// ** FIND THE USER CORRESPONDING THE LINK IN LOGIN TABLE **//
					PasswordResetEntity loginResetEntity = findByPrToken.get();
					System.out.println("loginResetEntity.getUser_id() " + loginResetEntity.getUser_id());
					Optional<AdminLoginEntity> findById = loginRepository.findById((loginResetEntity.getUser_id()));
					Optional<AdminLoginEntity> findByEmail = loginRepository.findByEmail((loginResetEntity.getEmail()));
					JsonObject jo = new JsonObject();
					if (findByEmail.isPresent() && findByEmail.get().getUid().equals(findById.get().getUid())) {
						// ** CREATE NEW PASSWORD AND SAVE **//
						AdminLoginEntity loginEntity = findById.get();
						loginEntity.setPassword(passwordEncoder.encode(globalEntity.getNewPass()));
						AdminLoginEntity save = loginRepository.save(loginEntity);
						if (save.equals(null)) {
							throw new CustomException("Data not save! try again");
						} else {
							loginResetEntity.setStatus("DEACTIVE");
							PasswordResetEntity save2 = loginResetRepo.save(loginResetEntity);
							jo.addProperty("senderMailId", save2.getEmail());
							return new GlobalResponse("SUCCESS", "Password changed successfully", 200);
						}
					} else {

						Optional<DesignerLoginEntity> findByIdDesigner = designerLoginRepo
								.findById((loginResetEntity.getUser_id()));
						Optional<DesignerLoginEntity> findByEmailDesigner = designerLoginRepo
								.findByEmail((loginResetEntity.getEmail()));
						if (findByEmailDesigner.isPresent()
								&& findByEmailDesigner.get().getUid().equals(findByIdDesigner.get().getUid())) {
							DesignerLoginEntity loginEntity = findByIdDesigner.get();
							loginEntity.setPassword(passwordEncoder.encode(globalEntity.getNewPass()));
							DesignerLoginEntity save = designerLoginRepo.save(loginEntity);
							if (save.equals(null)) {
								throw new CustomException("Data not save! try again");
							} else {
								loginResetEntity.setStatus("DEACTIVE");
								PasswordResetEntity save2 = loginResetRepo.save(loginResetEntity);
								jo.addProperty("senderMailId", save2.getEmail());
								return new GlobalResponse("SUCCESS", "Password generate successfully", 200);
							}
						} else {
							Optional<UserLoginEntity> findByUserId = userLoginRepo
									.findById((long) loginResetEntity.getUser_id());
							Optional<UserLoginEntity> findByUserEmail = userLoginRepo
									.findByEmail(loginResetEntity.getEmail());
							if (!findByUserEmail.isPresent())
								throw new CustomException("Username is not found");
							UserLoginEntity loginEntity = findByUserId.get();
							loginEntity.setPassword(passwordEncoder.encode(globalEntity.getNewPass()));
							UserLoginEntity save = userLoginRepo.save(loginEntity);
							if (save.equals(null)) {
								throw new CustomException("Data not save! try again");
							} else {
								loginResetEntity.setStatus("DEACTIVE");
								PasswordResetEntity save2 = loginResetRepo.save(loginResetEntity);
								jo.addProperty("senderMailId", save2.getEmail());
								return new GlobalResponse("SUCCESS", "Password changed successfully", 200);
							}
						}

//						jo.addProperty("subject", "Password change successfully");
//						jo.addProperty("body", "Hello " + jo.get("senderMailId") + ""
//								+ ",\n                           "
//								+ " your password have been changed." );
//						jo.addProperty("enableHtml", false);
//						try {
//							Unirest.setTimeouts(0, 0);
//							HttpResponse<String> response = Unirest.post("http://localhost:8080/dev/auth/sendMail")
//							  .header("Content-Type", "application/json")
//							  .body(jo.toString())
//							  .asString();
//						}catch(Exception e) {
//							System.out.println(e.getMessage());
//						}

					}

				} else {
					throw new CustomException("This link has been expierd");
				}
			} else {
				throw new CustomException("This URL is not valid");
			}

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@PostMapping("/changePassword")
	@Description("Using This Link user Can Change The Password After Login.")
	public GlobalResponse changePassword(@RequestBody GlobalEntity globalEntity,
			@RequestHeader(name = "Authorization") String token) {

		LOGGER.info("Inside - EcomAuthController.changePassword()");
		try {
			if (!token.equals(null)) {
				Optional<AdminLoginEntity> findByUserName = loginRepository.findByEmail(globalEntity.getUserName());
				Optional<DesignerLoginEntity> findByUserNameDesigner = designerLoginRepo
						.findByEmail(globalEntity.getUserName());
				Optional<UserLoginEntity> findByUserEmail = userLoginRepo.findByEmail(globalEntity.getUserName());
				if (!findByUserName.isPresent() && !findByUserNameDesigner.isPresent() && !findByUserEmail.isPresent())
					throw new CustomException("Username not Found");
				if (globalEntity.getUserName().equals(jwtUtil.extractUsername(token.substring(7)))) {
					try {
						if (!passwordEncoder.matches(globalEntity.getOldPass(), findByUserName.get().getPassword()))
							throw new CustomException("Old password is not valid");
						AdminLoginEntity loginEntity = findByUserName.get();
						loginEntity.setPassword(passwordEncoder.encode(globalEntity.getNewPass()));
						AdminLoginEntity save = loginRepository.save(loginEntity);
						return new GlobalResponse("SUCCESS", "Password changed successfully", 200);
					} catch (Exception e) {
						try {
							if (!passwordEncoder.matches(globalEntity.getOldPass(),
									findByUserNameDesigner.get().getPassword()))
								throw new CustomException("Old password is not valid");
							DesignerLoginEntity loginEntity = findByUserNameDesigner.get();
							loginEntity.setPassword(passwordEncoder.encode(globalEntity.getNewPass()));
							DesignerLoginEntity save = designerLoginRepo.save(loginEntity);
							return new GlobalResponse("SUCCESS", "Password changed successfully", 200);

						} catch (Exception Z) {
							try {
								if (!passwordEncoder.matches(globalEntity.getOldPass(),
										findByUserEmail.get().getPassword()))
									throw new CustomException("Old password is not valid");
								UserLoginEntity loginEntity = findByUserEmail.get();
								loginEntity.setPassword(passwordEncoder.encode(globalEntity.getNewPass()));
								UserLoginEntity save = userLoginRepo.save(loginEntity);
								return new GlobalResponse("SUCCESS", "Password changed successfully", 200);
							} catch (Exception o) {
								throw new CustomException("Old password is not valid");
							}

						}

					}

				} else {
					throw new CustomException("Username not found");
				}
			} else {
				throw new CustomException("Token not valid");
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@GetMapping("/info/{role}/{id}")
	@Description(value = "Using this API user/designer/admin can get his all details")
	public ResponseEntity<?> getDetails(@PathVariable("role") String role, @PathVariable("id") String id) {
		
		LOGGER.info("Inside - EcomAuthController.getDetails()");
		try {
			if (role.equals("USER")) {
				Optional<UserLoginEntity> findByEmail = userLoginRepo.findByEmail(id);
				if (findByEmail.isPresent()) {
					return ResponseEntity.ok(findByEmail);
				} else {
					throw new CustomException("Email not present");
				}
			} else if (role.equals("DESIGNER")) {
				Optional<DesignerLoginEntity> findByEmail = designerLoginRepo.findByEmail(id);
				if (findByEmail.isPresent()) {
					System.out.println(Long.parseLong(findByEmail.get().getUid().toString()));
					return ResponseEntity.ok(
							designerProfileRepo.findByDesignerId(Long.parseLong(findByEmail.get().getUid().toString())));
				} else {
					throw new CustomException("Email not present");
				}
			} else {
				Optional<AdminLoginEntity> findByEmail = loginRepository.findByEmail(id);
				if (findByEmail.isPresent()) {
					return ResponseEntity.ok(findByEmail);
				} else {
					throw new CustomException("Email not present");
				}
			}
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	
	@GetMapping("/Present/{email}")
	public ResponseEntity<?> checkUserPresent(@PathVariable("email") String email) {
		JsonObject jsObj = new JsonObject();
		if(userLoginRepo.findByEmail(email).isPresent() || designerLoginRepo.findByEmail(email).isPresent() || loginRepository.findByEmail(email).isPresent())
			jsObj.addProperty("isPresent", true);
		else
			jsObj.addProperty("isPresent", false);
		return ResponseEntity.ok(new Json(jsObj.toString()) );
	}

	@GetMapping("/admin/testapi")
	public String test() throws ClassNotFoundException {
		Class<?> forName = Class.forName("com.divatt.auth.controller.EcomAuthController");
		int MCount = forName.getDeclaredMethods().length;
		Class<?>[] interfaces = forName.getInterfaces();
		Class<?> theFirstAndOnlyInterface = interfaces[0];

//		return theFirstAndOnlyInterface.getMethods().length;
		return theFirstAndOnlyInterface.getMethods().length + " " + MCount;
	}

}
