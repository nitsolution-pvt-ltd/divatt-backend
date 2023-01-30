package com.divatt.designer.servicesImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.divatt.designer.constant.MessageConstant;
import com.divatt.designer.constant.RestTemplateConstants;
import com.divatt.designer.entity.account.AccountEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.AccountService;
import com.google.gson.Gson;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${spring.profiles.active}")
	private String contextPath;

	@Value("${host}")
	private String host;

	@Value("${interfaceId}")
	private String interfaceId;

	@Autowired
	private Gson gson;
	
	@Value("${DESIGNER}")
	private String DESIGNER_SERVICE;

	@Value("${AUTH}")
	private String AUTH_SERVICE;

	@Value("${ADMIN}")
	private String ADMIN_SERVICE;

	@Value("${USER}")
	private String USER_SERVICE;

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
				restTemplate.postForObject(ADMIN_SERVICE+RestTemplateConstants.ACCOUNT_ADD, accountEntity,
						AccountEntity.class);
				return ResponseEntity.ok().body(new GlobalResponce(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ACCOUNT_ADDED_MESSAGE.getMessage(), HttpStatus.OK.value()));
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
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/designerAccount/view/" + accountId, "Success", HttpStatus.OK);
			}
			try {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/designerAccount/view/" + accountId, "Success",
							HttpStatus.OK);
				}
				ResponseEntity<AccountEntity> accountEntity = restTemplate.getForEntity(ADMIN_SERVICE+
						RestTemplateConstants.ACCOUNT_VIEW_BY_ID + accountId, AccountEntity.class);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/designerAccount/view/" + accountId,
							gson.toJson(accountEntity), HttpStatus.OK);
				}
				return accountEntity;
			} catch (Exception exception) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("<Application name:{}>,<Request URL:{}>,<Response message:{}>,<Response code:{}>",
							interfaceId, host + contextPath + "/designerAccount/add", exception.getMessage(),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
				throw new CustomException(exception.getLocalizedMessage());
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/designerAccount/view/" + accountId, e.getLocalizedMessage(),
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
						host + contextPath + "/designerAccount/update/" + accountId, "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/designerAccount/update/" + accountId, gson.toJson(accountEntity),
						HttpStatus.OK);
			}
			try {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/designerAccount/update/" + accountId, "Success",
							HttpStatus.OK);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/designerAccount/update/" + accountId,
							gson.toJson(accountEntity), HttpStatus.OK);
				}
				restTemplate.put(ADMIN_SERVICE+RestTemplateConstants.ACCOUNT_UPDATE_BY_ID + accountId, accountEntity);
				return ResponseEntity.ok().body(new GlobalResponce(MessageConstant.SUCCESS.getMessage(),
						MessageConstant.ACCOUNT_UPDATED_MESSAGE.getMessage(), HttpStatus.OK.value()));
			} catch (Exception e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/designerAccount/update/" + accountId,
							e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
				throw new CustomException(e.getLocalizedMessage());
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/designerAccount/update/" + accountId, e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			throw new CustomException(e.getLocalizedMessage());
		}

	}

	public ResponseEntity<?> getAccountDetails(int page, int limit, String sort, String sortName, Boolean isDeleted,
			String keyword, String designerReturn, String serviceCharge, String govtCharge, String userOrder,
			String ReturnStatus, Optional<String> sortBy, String settlement, int year, int month, String designerId) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountServiceImpl.getAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountServiceImpl.getAccountDetails()");
		}
		try {
			String url = ADMIN_SERVICE+RestTemplateConstants.ACCOUNT_LIST + "?page=" + page + "&limit=" + limit + "&sort="
					+ sort + "&sortName=" + sortName + "&isDeleted=" + isDeleted + "&keyword=" + keyword
					+ "&designerReturn=" + designerReturn + "&serviceCharge=" + serviceCharge + "&govtCharge="
					+ govtCharge + "&userOrder=" + userOrder + "&ReturnStatus=" + ReturnStatus + "&sortBy=" + sortBy
					+ "&settlement=" + settlement + "&year=" + year + "&month=" + month + "&designerId=" + designerId;
			try {
				ResponseEntity<String> exchange = restTemplate.getForEntity(url, String.class);
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/designerAccount/list", "Success", HttpStatus.OK);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/designerAccount/list", gson.toJson(exchange),
							HttpStatus.OK);
				}
				return exchange;
			} catch (

			Exception e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
							interfaceId, host + contextPath + "/designerAccount/list", e.getLocalizedMessage(),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
				throw new CustomException(e.getLocalizedMessage());
			}
		} catch (

		Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}", interfaceId,
						host + contextPath + "/designerAccount/list", e.getLocalizedMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			throw new CustomException(e.getLocalizedMessage());
		}

	}

}
