package com.divatt.user.controller;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.user.entity.OrederAndPaymentGlobalEntity;
import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
import com.divatt.user.exception.CustomException;
import com.divatt.user.repo.OrderDetailsRepo;
import com.divatt.user.repo.UserAddressRepo;
import com.divatt.user.response.GlobalResponse;
import com.divatt.user.services.OrderAndPaymentService;
import com.divatt.user.services.SequenceGenerator;




@RestController
@RequestMapping("/userOrder")
public class OrderAndPaymentContoller {
	
	
	@Autowired
	private OrderDetailsRepo orderDetailsRepo;

	@Autowired
	private OrderAndPaymentService orderAndPaymentService;

	@Autowired
	private SequenceGenerator sequenceGenerator;
	
	@Autowired
	private UserAddressRepo userAddressRepo;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderAndPaymentContoller.class);
	
	
	@PostMapping("/razorpay/create")
	public ResponseEntity<?> postRazorpayOrderCreate(@Valid @RequestBody OrderDetailsEntity orderDetailsEntity) {
		LOGGER.info("Inside - OrderAndPaymentContoller.postRazorpayOrderCreate()");

		try {
			return this.orderAndPaymentService.postRazorpayOrderCreateService(orderDetailsEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@PostMapping("/payment/add")
	public GlobalResponse postOrderPaymentDetails(@Valid @RequestBody OrderPaymentEntity orderPaymentEntity) {
		LOGGER.info("Inside - OrderAndPaymentContoller.postOrderPaymentDetails()");

		try {
			return this.orderAndPaymentService.postOrderPaymentService(orderPaymentEntity);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	} 
	
	@RequestMapping(value = { "/payment/list" }, method = RequestMethod.GET)
	public Map<String, Object> getOrderPaymentDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
		    @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderPaymentDetails()");

		try {
			return this.orderAndPaymentService.getOrderPaymentService(page, limit, sort, sortName, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addOrder(@RequestBody OrederAndPaymentGlobalEntity orederAndPaymentGlobalEntity){
		LOGGER.info("Inside - OrderAndPaymentContoller.addOrder()");
		try {
			OrderDetailsEntity orderDetailsEntity = orederAndPaymentGlobalEntity.getOrderDetailsEntity();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			String format = formatter.format(date);
			String orderId = "OR"+System.currentTimeMillis();
			orderDetailsEntity.setId(sequenceGenerator.getNextSequence(OrderDetailsEntity.SEQUENCE_NAME));
			orderDetailsEntity.setOrderId("OR"+System.currentTimeMillis());
			orderDetailsEntity.setCreatedOn(format);
			orderDetailsRepo.save(orderDetailsEntity);
			OrderPaymentEntity orderPaymentEntity = orederAndPaymentGlobalEntity.getOrderPaymentEntity();
			orderPaymentEntity.setOrderId(orderId);
			postOrderPaymentDetails(orderPaymentEntity);
			return ResponseEntity.ok(new GlobalResponse("SUCCESS", "Updated successfully", 200));
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
	
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public Map<String, Object> getOrderDetails(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "createdOn") String sortName,
		    @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy) {
		LOGGER.info("Inside - OrderAndPaymentContoller.getOrderDetails()");

		try {
			return this.orderAndPaymentService.getOrders(page, limit, sort, sortName, keyword, sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	
	
}
