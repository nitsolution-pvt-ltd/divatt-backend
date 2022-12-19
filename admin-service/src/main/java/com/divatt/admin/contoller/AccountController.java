package com.divatt.admin.contoller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.admin.entity.AccountEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;
	
	@Value("${spring.profiles.active}")
	private String contextPath;

	@Value("${host}")
	private String host;
	
	@Value("${interfaceId}")
	private String interfaceId;
	
	@PostMapping("/add")
	public GlobalResponse postAccountDetails(@Valid @RequestBody AccountEntity accountEntity) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.postAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.postAccountDetails()");
		}
		
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "/account/add", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "/account/add", "Success", HttpStatus.OK);
			}
			return this.accountService.postAccountDetails(accountEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@GetMapping("/view/{accountId}")
	public ResponseEntity<?> viewAccountDetails(@PathVariable() long accountId) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.viewAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.viewAccountDetails()");
		}
		
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "/account/view/"+accountId, "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "/account/view/"+accountId, "Success", HttpStatus.OK);
			}
			return this.accountService.viewAccountDetails(accountId);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "/account/view/"+accountId, e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PutMapping("/update/{accountId}")
	public GlobalResponse putAccountDetails(@Valid @RequestBody AccountEntity accountEntity, @PathVariable() Integer accountId) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.putAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.putAccountDetails()");
		}
		
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "/account/update/"+accountId, "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "/account/update/"+accountId, "Success", HttpStatus.OK);
			}
			return this.accountService.putAccountDetails(accountId,accountEntity);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "/account/update/"+accountId, e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getAccountDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "_id") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "") String designerReturn, @RequestParam(defaultValue = "") String serviceCharge,
			@RequestParam(defaultValue = "") String govtCharge, @RequestParam(defaultValue = "") String userOrder,
			@RequestParam(defaultValue = "") String ReturnStatus,
			@RequestParam Optional<String> sortBy) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.getAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.getAccountDetails()");
		}

		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "/account/list", "Success", HttpStatus.OK);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "/account/list", "Success", HttpStatus.OK);
			}
			return this.accountService.getAccountDetails(page, limit, sort, sortName, isDeleted, keyword, designerReturn, serviceCharge, govtCharge, userOrder, ReturnStatus, sortBy);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
						interfaceId, host + contextPath + "/account/list", e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e.getMessage());
		}

	}

}
