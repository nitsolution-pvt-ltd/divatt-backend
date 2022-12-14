package com.divatt.admin.contoller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.admin.entity.AccountEntity;
import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.category.CategoryEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;
	
	
	@PostMapping("/add")
	public GlobalResponse postAccountDetails(@Valid @RequestBody AccountEntity accountEntity) {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Inside - AccountController.postAccountDetails()");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inside - AccountController.postAccountDetails()");
		}
		
		try {
			return this.accountService.postAccountDetails(accountEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getAccountDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDeleted, @RequestParam(defaultValue = "") String keyword,
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
						"Admin Service", "account/list", "Success", HttpStatus.OK);
			}
			return this.accountService.getAccountDetails(page, limit, sort, sortName, isDeleted, keyword, sortBy);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Application name: {},Request URL: {},Response message: {},Response code: {}",
						"Admin Service", "account/list", e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
			}
			throw new CustomException(e.getMessage());
		}

	}

}
