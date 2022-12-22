package com.divatt.designer.servicesImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.divatt.designer.config.JWTConfig;
import com.divatt.designer.constant.MessageConstant;
import com.divatt.designer.constant.RestTemplateConstant;
import com.divatt.designer.entity.account.AccountEntity;
import com.divatt.designer.entity.profile.DesignerLoginEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.repo.DesignerLoginRepo;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.AccountService;
import com.google.gson.Gson;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JWTConfig config;

	@Autowired
	private DesignerLoginRepo designerLoginRepo;

	@Value("${spring.profiles.active}")
	private String contextPath;

	@Value("${host}")
	private String host;

	@Value("${interfaceId}")
	private String interfaceId;

	@Autowired
	private Gson gson;

	public ResponseEntity<?> postAccountDetails(@RequestBody AccountEntity accountEntity, String token) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountServiceImpl.postAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountServiceImpl.postAccountDetails()");
		}

		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/designerAccount/add", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/designerAccount/add", gson.toJson(accountEntity), HttpStatus.OK);
			}
			try {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/designerAccount/add", "Success", HttpStatus.OK);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/designerAccount/add", gson.toJson(accountEntity),
							HttpStatus.OK);
				}
				restTemplate.postForObject(RestTemplateConstant.ACCOUNT_ADD.getMessage(), accountEntity,
						AccountEntity.class);
				return ResponseEntity.ok().body(new GlobalResponce(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ACCOUNT_ADDED_MESSAGE.getMessage(), HttpStatus.OK.value()));
			} catch (HttpStatusCodeException ex) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("<Application name:{}>,<Request URL:{}>,<Response message:{}>,<Response code:{}>",
							interfaceId, host + contextPath + "/designerAccount/add", ex.getResponseBodyAsString(),
							ex.getStatusCode());
				}
				return new ResponseEntity<>(ex.getResponseBodyAsString(), ex.getStatusCode());
			} catch (Exception exception) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("<Application name:{}>,<Request URL:{}>,<Response message:{}>,<Response code:{}>",
							interfaceId, host + contextPath + "/designerAccount/add", exception.getMessage(),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
				throw new CustomException(exception.getLocalizedMessage());
			}
		} catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("<Application name:{}>,<Request URL:{}>,<Response message:{}>,<Response code:{}>",
						interfaceId, host + contextPath + "/designerAccount/add", exception.getMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			throw new CustomException(exception.getLocalizedMessage());
		}

	}

	public ResponseEntity<?> viewAccountDetails(long accountId, String token) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountServiceImpl.viewAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountServiceImpl.viewAccountDetails()");
		}

		try {
			String extractUsername = config.extractUsername(token.substring(7));
			LOGGER.info(extractUsername);
			Optional<DesignerLoginEntity> findByEmail = designerLoginRepo.findByEmail(extractUsername);
			LOGGER.info(findByEmail + "Inside FindbyEmail");
			if (!findByEmail.isEmpty()) {
				ResponseEntity<AccountEntity> accountEntity = restTemplate.getForEntity(
						RestTemplateConstant.ACCOUNT_VIEW_BY_ID.getMessage() + accountId, AccountEntity.class);

				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/account/view/" + accountId, "Success", HttpStatus.OK);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/account/view/" + accountId, gson.toJson(accountEntity),
							HttpStatus.OK);
				}
				return accountEntity;
			} else
				throw new CustomException(MessageConstant.UNAUTHORIZED.getMessage());

		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/view/" + accountId, e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<?> putAccountDetails(long accountId, @RequestBody AccountEntity accountEntity, String token) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountServiceImpl.putAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountServiceImpl.putAccountDetails()");
		}

		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/update/" + accountId, "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/update/" + accountId, gson.toJson(accountEntity), HttpStatus.OK);
			}
			String extractUsername = config.extractUsername(token.substring(7));
			LOGGER.info(extractUsername);
			Optional<DesignerLoginEntity> findByEmail = designerLoginRepo.findByEmail(extractUsername);
			LOGGER.info(findByEmail + "Inside FindbyEmail");
			if (!findByEmail.isEmpty()) {
				restTemplate.put(RestTemplateConstant.ACCOUNT_UPDATE_BY_ID.getMessage() + accountId, accountEntity);
				return ResponseEntity.ok().body(new GlobalResponce(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ACCOUNT_UPDATED_MESSAGE.getMessage(), HttpStatus.OK.value()));
			} else
				throw new CustomException(MessageConstant.UNAUTHORIZED.getMessage());
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/update/" + accountId, e.getLocalizedMessage(),
						HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e.getMessage());
		}

	}

	public ResponseEntity<?> getAccountDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, String designerReturn, String serviceCharge, String govtCharge, String userOrder,
			String ReturnStatus, Optional<String> sortBy, String token) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountServiceImpl.getAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountServiceImpl.getAccountDetails()");
		}
		try {
			String extractUsername = config.extractUsername(token.substring(7));
			LOGGER.info(extractUsername);
			Optional<DesignerLoginEntity> findByEmail = designerLoginRepo.findByEmail(extractUsername);
			LOGGER.info(findByEmail + "Inside FindbyEmail");
			if (!findByEmail.isEmpty()) {
				String url = RestTemplateConstant.ACCOUNT_LIST.getMessage();
				HttpHeaders headers = new HttpHeaders();
				headers.set("Accept", "application/json");

				Map<String, Object> params = new HashMap<>();
				params.put("page", page);
				params.put("limit", limit);
				params.put("sort", sort);
				params.put("sortName", sortName);
				params.put("isDeleted", isDeleted);
				params.put("keyword", keyword);
				params.put("designerReturn", designerReturn);
				params.put("serviceCharge", serviceCharge);
				params.put("govtCharge", govtCharge);
				params.put("userOrder", userOrder);
				params.put("ReturnStatus", ReturnStatus);
				params.put("sortBy", sortBy);
				HttpEntity entity = new HttpEntity(headers);

				ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, String.class,
						params);
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/account/list", "Success", HttpStatus.OK);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/account/list", gson.toJson(exchange), HttpStatus.OK);
				}
				return exchange;
			} else
				throw new CustomException(MessageConstant.UNAUTHORIZED.getMessage());
		} catch (

		Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/account/list", e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e.getMessage());
		}

	}

}
