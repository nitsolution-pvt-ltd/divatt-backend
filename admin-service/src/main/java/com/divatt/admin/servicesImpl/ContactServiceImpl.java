package com.divatt.admin.servicesImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.divatt.admin.DTO.BankDetails;
import com.divatt.admin.DTO.Bank_account;
import com.divatt.admin.DTO.ContDTO;
import com.divatt.admin.DTO.ContactDTO;
import com.divatt.admin.DTO.FundsDTO;
import com.divatt.admin.DTO.PayDTO;
import com.divatt.admin.DTO.PayOutDTO;
import com.divatt.admin.DTO.RazorpayXContactDto;
import com.divatt.admin.DTO.RazorpayXPaymentDTO;
import com.divatt.admin.config.JWTConfig;
import com.divatt.admin.constant.MessageConstant;
import com.divatt.admin.constant.RestTemplateConstants;
import com.divatt.admin.entity.AccountEntity;
import com.divatt.admin.entity.DesignerProfile;
import com.divatt.admin.entity.DesignerProfileEntity;
import com.divatt.admin.entity.DesignerReturnAmount;
import com.divatt.admin.entity.FundsBankAccount;
import com.divatt.admin.entity.LoginEntity;
import com.divatt.admin.entity.PayOutDetails;
import com.divatt.admin.entity.RazorpayX;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.repo.AccountRepo;
import com.divatt.admin.repo.LoginRepository;
import com.divatt.admin.services.ContactService;
import com.google.gson.Gson;
import org.springframework.http.MediaType;

@Service
public class ContactServiceImpl implements ContactService {

	@Value("${key}")
	private String apiKey;

	@Value("${secretKey}")
	private String apiSecretKey;

	@Value("${spring.profiles.active}")
	private String contextPath;

	@Value("${host}")
	private String host;

	@Value("${interfaceId}")
	private String interfaceId;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private JWTConfig jwtconfig;

	@Value("${DESIGNER}")
	private String DESIGNER_SERVICE;

	@Value("${ADMIN}")
	private String ADMIN_SERVICE;

	@Autowired
	private Gson gson;

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactDTO.class);

	public ResponseEntity<?> addcontactsService(ContactDTO contactDTO) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactServiceImpl.addcontactsService()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactServiceImpl.addcontactsService()");
		}
		try {
			Long designerId = contactDTO.getDesignerId();
			DesignerProfileEntity body = restTemplate
					.getForEntity(DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_IDD + designerId,
							DesignerProfileEntity.class)
					.getBody();
			ContDTO contDTO = new ContDTO();
			contDTO.setReference_id("RI_" + System.currentTimeMillis());
			String name = body.getDesignerName();
			String email = body.getDesignerProfile().getEmail();
			String mobileNo2 = body.getDesignerProfile().getMobileNo();
			contDTO.setName(name);
			contDTO.setEmail(email);
			long mobileNo = Long.parseLong(mobileNo2);
			contDTO.setContact(mobileNo);
			contDTO.setType(contactDTO.getType());
			contDTO.setNotes(contactDTO.getNotes());
			body.setNotes(contactDTO.getNotes());
			body.setType(contactDTO.getType());
			String accountNumber = body.getDesignerPersonalInfoEntity().getBankDetails().getAccountNumber();
			String ifscCode = body.getDesignerPersonalInfoEntity().getBankDetails().getIfscCode();
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth(apiKey, apiSecretKey);
			HttpEntity<?> entity = new HttpEntity<Object>(contDTO, headers);
			try {
				ResponseEntity<RazorpayXContactDto> forEntity2 = restTemplate.exchange(
						RestTemplateConstants.RAZORPAYX_CONTACTS_URL, HttpMethod.POST, entity,
						RazorpayXContactDto.class);
				RazorpayXContactDto body2 = forEntity2.getBody();
				String id = body2.getId();
				DesignerProfileEntity designerProfile = new DesignerProfileEntity();
				RazorpayX rozarpayX = new RazorpayX();
				List<String> contacts = new ArrayList<>();

				if (contacts.size() > 0) {

					contacts.add(id);
				} else {
					contacts.add(id);
				}
				rozarpayX.setContacts(contacts);
				String contact1 = contacts.get(0);
				FundsDTO fundsDto = new FundsDTO();
				Bank_account bank_account = new Bank_account();
				fundsDto.setAccount_type("bank_account");
				fundsDto.setContact_id(contact1);
				bank_account.setAccount_number(accountNumber);
				bank_account.setIfsc(ifscCode);
				bank_account.setName(name);
				fundsDto.setBank_account(bank_account);
				HttpEntity<?> entityForFunds = new HttpEntity<Object>(fundsDto, headers);
				try {
					ResponseEntity<RazorpayXFundsDto> exchange = restTemplate.exchange(
							RestTemplateConstants.RAZORPAYX_FUNDS_URL, HttpMethod.POST, entityForFunds,
							RazorpayXFundsDto.class);
					RazorpayXFundsDto body3 = exchange.getBody();
					String id2 = body3.getId();
					FundsBankAccount fundsBankAccount = new FundsBankAccount();
					List<String> fundsBankId = new ArrayList<>();
					if (fundsBankId.size() > 0) {

						fundsBankId.add(id2);
					} else {
						fundsBankId.add(id2);
					}
					fundsBankAccount.setFundsBankId(fundsBankId);
					rozarpayX.setFundsBankAccount(fundsBankAccount);

					rozarpayX.setContacts(contacts);
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
								interfaceId, host + contextPath + "/payOutDetails/payOutList", "Success",
								HttpStatus.OK);
					}
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
								interfaceId, host + contextPath + "/payOutDetails/payOutList", gson.toJson(rozarpayX),
								HttpStatus.OK);
					}

					return new ResponseEntity<>(rozarpayX, HttpStatus.OK);

				} catch (Exception e) {
					throw new CustomException(e.getMessage());
				}
			} catch (Exception e1) {
				throw new CustomException(e1.getMessage());
			}

		} catch (HttpStatusCodeException e2) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/contacts", e2.getResponseBodyAsString(),
						e2.getStatusCode());
			}
			throw new CustomException(e2.getMessage());
		} catch (Exception e3) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/contacts", e3.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e3.getMessage());
		}
	}

	public ResponseEntity<?> addFunds(Long designerId, FundsDTO fundsDto) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactServiceImpl.addFunds()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactServiceImpl.addFunds()");
		}
		try {
			DesignerProfileEntity body = restTemplate
					.getForEntity(DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_IDD + designerId,
							DesignerProfileEntity.class)
					.getBody();
			;
			List<String> contacts2 = body.getRazorpayX().getContacts();
			List<String> contacts = body.getRazorpayX().getContacts();
			String account_type = fundsDto.getAccount_type();
			body.setAccount_type(account_type);
			contacts.stream().forEach(e -> {
				fundsDto.setContact_id(e);
			});
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth(apiKey, apiSecretKey);
			HttpEntity<?> entity = new HttpEntity<Object>(fundsDto, headers);
			try {
				ResponseEntity<RazorpayXFundsDto> forEntity2 = restTemplate.exchange(
						RestTemplateConstants.RAZORPAYX_FUNDS_URL, HttpMethod.POST, entity, RazorpayXFundsDto.class);
				RazorpayXFundsDto body2 = forEntity2.getBody();
				String id = body2.getId();

				RazorpayX rozarpayX = new RazorpayX();

				FundsBankAccount fundsBankAccount = new FundsBankAccount();
				List<String> fundsBankId = new ArrayList<>();

				if (fundsBankId.size() > 0) {

					fundsBankId.add(id);
				} else {
					fundsBankId.add(id);
				}
				fundsBankAccount.setFundsBankId(fundsBankId);
				rozarpayX.setFundsBankAccount(fundsBankAccount);
				rozarpayX.setContacts(contacts2);

				return new ResponseEntity<>(rozarpayX, HttpStatus.OK);
			} catch (Exception e) {
				throw new CustomException(e.getMessage());
			}
		} catch (HttpStatusCodeException e2) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/funds", e2.getResponseBodyAsString(), e2.getStatusCode());
			}
			throw new CustomException(e2.getMessage());
		} catch (Exception e3) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/funds", e3.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e3.getMessage());
		}

	}

	public ResponseEntity<?> getContactListById(@RequestParam Long designerId) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactServiceImpl.getContactListById()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactServiceImpl.getContactListById()");
		}
		try {
			DesignerProfileEntity body = restTemplate
					.getForEntity(DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_IDD + designerId,
							DesignerProfileEntity.class)
					.getBody();
			List<String> contacts = body.getRazorpayX().getContacts();
			List<Object> getContactDetails = new ArrayList<>();
			try {
				if (contacts.size() > 0) {
					contacts.stream().forEach(e -> {
						HttpHeaders headers = new HttpHeaders();
						headers.setBasicAuth(apiKey, apiSecretKey);
						HttpEntity<?> entity = new HttpEntity<Object>(null, headers);
						ResponseEntity<Object> forEntity2 = restTemplate.exchange(
								RestTemplateConstants.RAZORPAYX_GET_CONTACTS_BY_ID + e, HttpMethod.GET, entity,
								Object.class);
						Object body2 = forEntity2.getBody();
						getContactDetails.add(body2);

					});
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
								interfaceId, host + contextPath + "/payOutDetails/payOutList", "Success",
								HttpStatus.OK);
					}
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
								interfaceId, host + contextPath + "/payOutDetails/payOutList",
								gson.toJson(getContactDetails), HttpStatus.OK);
					}
					return new ResponseEntity<>(getContactDetails, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(MessageConstant.NO_DATA.getMessage(), HttpStatus.NOT_FOUND);
				}

			} catch (Exception e) {
				throw new CustomException(e.getMessage());
			}
		} catch (HttpStatusCodeException e2) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getContactsListById", e2.getResponseBodyAsString(),
						e2.getStatusCode());
			}
			throw new CustomException(e2.getMessage());
		} catch (Exception e3) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getContactsListById", e3.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e3.getMessage());
		}
	}

	public ResponseEntity<?> getContactList() {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactServiceImpl.getContactList()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactServiceImpl.getContactList()");
		}
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth(apiKey, apiSecretKey);
			HttpEntity<?> entity = new HttpEntity<Object>(null, headers);
			ResponseEntity<Object> forEntity2 = restTemplate.exchange(RestTemplateConstants.RAZORPAYX_GET_CONTACTS_ALL,
					HttpMethod.GET, entity, Object.class);
			Object contactList = forEntity2.getBody();
			return new ResponseEntity<>(contactList, HttpStatus.OK);
		} catch (HttpStatusCodeException e2) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getContactsList", e2.getResponseBodyAsString(),
						e2.getStatusCode());
			}
			throw new CustomException(e2.getMessage());
		} catch (Exception e1) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getContactsList", e1.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e1.getMessage());
		}
	}

	public ResponseEntity<?> getFundsList() {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactServiceImpl.getFundsList()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactServiceImpl.getFundsList()");
		}
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth(apiKey, apiSecretKey);
			HttpEntity<?> entity = new HttpEntity<Object>(null, headers);
			ResponseEntity<Object> forEntity2 = restTemplate.exchange(RestTemplateConstants.RAZORPAYX_GET_FUNDS_ALL,
					HttpMethod.GET, entity, Object.class);

			Object fundsList = forEntity2.getBody();
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/payOutList", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/payOutList", gson.toJson(fundsList), HttpStatus.OK);
			}
			return new ResponseEntity<>(fundsList, HttpStatus.OK);

		} catch (HttpStatusCodeException e2) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getFundsList", e2.getResponseBodyAsString(),
						e2.getStatusCode());
			}
			throw new CustomException(e2.getMessage());
		} catch (Exception e1) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getFundsList", e1.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e1.getMessage());
		}
	}

	public ResponseEntity<?> getFundsListById(@RequestParam Long designerId) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactServiceImpl.getFundsListById()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactServiceImpl.getFundsListById()");
		}
		try {
			DesignerProfileEntity body = restTemplate
					.getForEntity(DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_IDD + designerId,
							DesignerProfileEntity.class)
					.getBody();
			try {
				if (body.getRazorpayX() == null) {
					ContactDTO contactDTO = new ContactDTO();
					contactDTO.setName(body.getDesignerName());
					contactDTO.setEmail(body.getDesignerProfile().getEmail());
					contactDTO.setContact(Long.parseLong(body.getDesignerProfile().getMobileNo()));
					ResponseEntity<DesignerProfileEntity> postForEntity = restTemplate.postForEntity(DESIGNER_SERVICE
							+ RestTemplateConstants.DESIGNER_POST_CONTS_URL + "?designerId=" + designerId, contactDTO,
							DesignerProfileEntity.class);
					DesignerProfileEntity body3 = postForEntity.getBody();
					body.setRazorpayX(body3.getRazorpayX());
					List<String> fundsBankAccount = body.getRazorpayX().getFundsBankAccount().getFundsBankId();
					List<Object> getFundDetails = new ArrayList<>();
					if (fundsBankAccount.size() > 0) {
						fundsBankAccount.stream().forEach(e -> {
							HttpHeaders headers = new HttpHeaders();
							headers.setBasicAuth(apiKey, apiSecretKey);
							HttpEntity<?> entity = new HttpEntity<Object>(null, headers);
							ResponseEntity<Object> forEntity2 = restTemplate.exchange(
									RestTemplateConstants.RAZORPAYX_GET_FUNDS_BY_ID + e, HttpMethod.GET, entity,
									Object.class);

							Object body2 = forEntity2.getBody();
							getFundDetails.add(body2);

						});
						return new ResponseEntity<>(getFundDetails, HttpStatus.OK);
					} else {
						return new ResponseEntity<>(MessageConstant.NO_DATA.getMessage(), HttpStatus.NOT_FOUND);
					}
				} else {
					List<String> fundsBankAccount = body.getRazorpayX().getFundsBankAccount().getFundsBankId();
					List<Object> getFundDetails = new ArrayList<>();
					try {
						if (fundsBankAccount.size() > 0) {
							fundsBankAccount.stream().forEach(e -> {

								HttpHeaders headers = new HttpHeaders();
								headers.setBasicAuth(apiKey, apiSecretKey);
								HttpEntity<?> entity = new HttpEntity<Object>(null, headers);
								ResponseEntity<Object> forEntity2 = restTemplate.exchange(
										RestTemplateConstants.RAZORPAYX_GET_FUNDS_BY_ID + e, HttpMethod.GET, entity,
										Object.class);
								Object body2 = forEntity2.getBody();
								getFundDetails.add(body2);
							});
							if (LOGGER.isInfoEnabled()) {
								LOGGER.info(
										"Application name: {},Request URL: {},Response message: {},Response code: {}",
										interfaceId, host + contextPath + "/payOutDetails/payOutList", "Success",
										HttpStatus.OK);
							}
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug(
										"Application name: {},Request URL: {},Response message: {},Response code: {}",
										interfaceId, host + contextPath + "/payOutDetails/payOutList",
										gson.toJson(getFundDetails), HttpStatus.OK);
							}
							return new ResponseEntity<>(getFundDetails, HttpStatus.OK);
						} else {
							return new ResponseEntity<>(MessageConstant.NO_DATA.getMessage(), HttpStatus.NOT_FOUND);
						}
					} catch (Exception e2) {
						throw new CustomException(e2.getMessage());
					}
				}
			} catch (Exception e1) {
				throw new CustomException(e1.getMessage());
			}
		} catch (HttpStatusCodeException e3) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getFundsListById", e3.getResponseBodyAsString(),
						e3.getStatusCode());
			}
			throw new CustomException(e3.getMessage());
		} catch (Exception e4) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/getFundsListById", e4.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e4.getMessage());
		}
	}

//	@Override
//	public ResponseEntity<?> getdesignerAccNo(Long designerId) {
//		if (LOGGER.isInfoEnabled()) {
//			LOGGER.info("Inside - ContactServiceImpl.addPayOut()");
//		}
//		if (LOGGER.isDebugEnabled()) {
//			LOGGER.debug("Inside - ContactServiceImpl.addPayOut()");
//		}
//		try {
//			List<String> designerAccNo = new ArrayList<>();
//			Map<String, Object> response = new HashMap<>();
//			RazorpayXFundsDto[] forEntity = restTemplate.getForEntity(
//					ADMIN_SERVICE + RestTemplateConstants.RAZORPAYX_GET_FUNDS_URL + "?designerId=" + designerId,
//					RazorpayXFundsDto[].class).getBody();
//			List<RazorpayXFundsDto> asList = Arrays.asList(forEntity);
//			for (RazorpayXFundsDto accId : asList) {
//				String account_number = accId.getBank_account().getAccount_number();
//				designerAccNo.add(account_number);
//				response.put("designerAccountNumber", account_number);
//			}
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		} catch (Exception e) {
//			throw new CustomException(e.getMessage());
//		}
//	}
	@Override
	public ResponseEntity<?> addPayOut(String token, PayOutDTO payOutDTO) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactServiceImpl.addPayOut()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactServiceImpl.addPayOut()");
		}
		try {
			Long designerId = payOutDTO.getDesignerId();
			String orderId = payOutDTO.getOrderId();
			int productId = payOutDTO.getProductId();
			String adminEmail = jwtconfig.extractUsername(token.substring(7));
			LoginEntity loginEntity = this.loginRepository.findByEmail(adminEmail).get();
			loginEntity.getRole();
			PayDTO payDTO = new PayDTO();
			payDTO.setAccount_number(payOutDTO.getAccount_number());
			payDTO.setFund_account_id(payOutDTO.getFund_account_id());
			payDTO.setAmount(payOutDTO.getAmount());
			payDTO.setCurrency(payOutDTO.getCurrency());
			payDTO.setMode(payOutDTO.getMode());
			payDTO.setNarration(payOutDTO.getNarration());
			payDTO.setNotes(payOutDTO.getNotes());
			payDTO.setPurpose(payOutDTO.getPurpose());
			payDTO.setQueue_if_low_balance(payOutDTO.getQueue_if_low_balance());
			payDTO.setReference_id("PORI_" + System.currentTimeMillis());
			try {
				AccountEntity accountEntity = this.accountRepo
						.findByDesignerIdAndOrderIdAndProductId(designerId, orderId, productId).get(0);
//				DesignerProfileEntity designerProfileData = restTemplate
//						.getForEntity(DESIGNER_SERVICE + RestTemplateConstants.DESIGNER_IDD + designerId,
//								DesignerProfileEntity.class)
//						.getBody();
//				List<String> fundsBankId = designerProfileData.getRazorpayX().getFundsBankAccount().getFundsBankId();
				SimpleDateFormat formatter = new SimpleDateFormat(MessageConstant.DATE_FORMAT.getMessage());
				Date dates = new Date();
				String format = formatter.format(dates);
//				if (fundsBankId.size() > 0) {
//					fundsBankId.stream().forEach(e -> {
//						payDTO.setFund_account_id(e);
//					});
//				} else {
//					throw new CustomException(MessageConstant.NO_ID_PRESENT.getMessage());
//				}
				HttpHeaders headers = new HttpHeaders();
				headers.setBasicAuth(apiKey, apiSecretKey);
				HttpEntity<?> entity = new HttpEntity<Object>(payDTO, headers);
				try {

					Map<String, Object> response = new HashMap<>();
					JSONObject payOutData = restTemplate.exchange(RestTemplateConstants.RAZORPAYX_PAYOUT_URL,
							HttpMethod.POST, entity, JSONObject.class).getBody();
					String payOutId = payOutData.get("id").toString();
					List<DesignerReturnAmount> designer_return_amount = accountEntity.getDesigner_return_amount();
					designer_return_amount.stream().forEach(e -> {
						e.setRazorpayXPaymentId(payOutId);
						e.setPayOutDateTime(format);
						e.setRole(loginEntity.getRole());
					});
					accountEntity.setDesigner_return_amount(designer_return_amount);
					try {
						accountRepo.save(accountEntity);	
					}catch (Exception e) {
						throw new CustomException("Data did not save into Database");
					}
					
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
								interfaceId, host + contextPath + "/payOutDetails/addPayOut", "Success", HttpStatus.OK);
					}
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
								interfaceId, host + contextPath + "/payOutDetails/addPayOut",
								gson.toJson(accountEntity), HttpStatus.OK);
					}
					response.put("reason", MessageConstant.SUCCESS.getMessage());
					response.put("message", MessageConstant.PAY_OUT_ID_ADDED.getMessage());
					response.put("status", 200);
					response.put("payOutId", payOutId);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} catch (Exception e) {
					throw new CustomException(e.getMessage());
				}
			} catch (Exception e1) {
				throw new CustomException(e1.getMessage());
			}

		} catch (HttpStatusCodeException e2) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/addPayOut", e2.getResponseBodyAsString(),
						e2.getStatusCode());
			}
			throw new CustomException(e2.getMessage());
		} catch (Exception e3) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/addPayOut", e3.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e3.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> getPayOutListById(String token, Long designerId, String orderId, int productId) {
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Inside - ContactServiceImpl.getPayOutListById()");
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Inside - ContactServiceImpl.getPayOutListById()");
			}
			String adminEmail = jwtconfig.extractUsername(token.substring(7));
			AccountEntity accountEntity = this.accountRepo
					.findByDesignerIdAndOrderIdAndProductId(designerId, orderId, productId).get(0);
			List<DesignerReturnAmount> designer_return_amount = accountEntity.getDesigner_return_amount();
			List<Object> payOutList = new ArrayList<>();
			if (designer_return_amount.size() > 0) {
				designer_return_amount.stream().forEach(e -> {
					HttpHeaders headers = new HttpHeaders();
					headers.setBasicAuth(apiKey, apiSecretKey);
					HttpEntity<?> entity = new HttpEntity<Object>(null, headers);
					try {
						Object payOutData = restTemplate.exchange(
								RestTemplateConstants.RAZORPAYX_GET_PAYOUT_BY_ID_URL + e.getRazorpayXPaymentId(),
								HttpMethod.GET, entity, Object.class).getBody();
						payOutList.add(payOutData);
					} catch (Exception exp) {
						throw new CustomException(exp.getMessage());
					}
				});
			}
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/payOutListById", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/payOutListById", gson.toJson(payOutList), HttpStatus.OK);
			}
			return new ResponseEntity<>(payOutList, HttpStatus.OK);
		} catch (HttpStatusCodeException e2) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/payOutListById", e2.getResponseBodyAsString(),
						e2.getStatusCode());
			}
			throw new CustomException(e2.getMessage());
		} catch (Exception e1) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/payOutListById", e1.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e1.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> getPayOutList(String token) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactServiceImpl.getPayOutList()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactServiceImpl.getPayOutList()");
		}
		try {
			String adminEmail = jwtconfig.extractUsername(token.substring(7));
			LoginEntity loginEntity = this.loginRepository.findByEmail(adminEmail).get();
			String accountNumber = loginEntity.getRazorpayXAccountNo();
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth(apiKey, apiSecretKey);
			HttpEntity<?> entity = new HttpEntity<Object>(null, headers);
			try {
				Object payOutData = restTemplate.exchange(
						RestTemplateConstants.RAZORPAYX_GET_ALL_PAYOUT_URL + "?account_number=" + accountNumber,
						HttpMethod.GET, entity, Object.class).getBody();
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/payOutDetails/payOutList", "Success", HttpStatus.OK);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/payOutDetails/payOutList", gson.toJson(payOutData),
							HttpStatus.OK);
				}
				return new ResponseEntity<>(payOutData, HttpStatus.OK);
			} catch (Exception e) {
				throw new CustomException(e.getMessage());
			}
		} catch (HttpStatusCodeException e2) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/payOutList", e2.getResponseBodyAsString(),
						e2.getStatusCode());
			}
			throw new CustomException(e2.getMessage());
		} catch (Exception e1) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/payOutList", e1.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e1.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> postRazorpayXHandle(RazorpayXPaymentDTO xPaymentDTO) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - ContactServiceImpl.postRazorpayXOrderHandle()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - ContactServiceImpl.postRazorpayXOrderHandle()");
		}
		try {
			LOGGER.info("Recieved Request Body From Webhook in ServiceImpl: "+xPaymentDTO);
			Map<String, Object> response = new HashMap<>();
			AccountEntity accountEntityData = accountRepo
					.findByRazorpayXPaymentId(xPaymentDTO.getPayload().getPayout().getEntity().getId()).get(0);
			String paymentStatus = xPaymentDTO.getPayload().getPayout().getEntity().getStatus();
			List<DesignerReturnAmount> designer_return_amount = accountEntityData.getDesigner_return_amount();
			designer_return_amount.stream().forEach(e -> {
				if(! StringUtils.isEmpty(paymentStatus) || ! paymentStatus.equals(null)) {
					e.setRazorPayPaymentStatus(paymentStatus);	
				}else {
				throw new CustomException(MessageConstant.PAYMENT_STATUS_NOT_FOUND.getMessage());	
				}
			});
			accountEntityData.setDesigner_return_amount(designer_return_amount);
			accountRepo.save(accountEntityData);			
			response.put("reason", MessageConstant.SUCCESS.getMessage());
			response.put("message", MessageConstant.PAYMENT_STATUS_UPDATE.getMessage());
			response.put("status", 200);
			response.put("response", xPaymentDTO);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (HttpStatusCodeException e2) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/razorpayX/handle", e2.getResponseBodyAsString(),
						e2.getStatusCode());
			}
			throw new CustomException(e2.getMessage());
		} catch (Exception e1) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/payOutDetails/razorpayX/handle", e1.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e1.getMessage());
		}
	}
}
