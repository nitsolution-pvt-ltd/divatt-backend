package com.divatt.user.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.UserDesignerRepo;
import com.divatt.user.repo.UserLoginRepo;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.SequenceGenerator;
import com.divatt.user.services.UserService;

public class OrderAndPaymentContoller {
	
	
	@Autowired
	private OrderDetailsRepo orderDetailsRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
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
