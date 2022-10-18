package com.divatt.designer.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.entity.InvoiceMainData;
import com.divatt.designer.entity.OrderSKUDetailsEntity;
import com.divatt.designer.entity.ProductInvoice;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderStatusController {

	@Autowired
	private OrderService orderService;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusController.class);

	@PutMapping("/orderStatus/{orderId}/{statusKeyword}")
	public GlobalResponce orderStatusUpdate(@PathVariable String orderId, @PathVariable String statusKeyword) {
		try {
			return this.orderService.changeStatus(orderId, statusKeyword);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/genrateDesignerInvoice/{orderId}")
	public ResponseEntity<?> genarateDesignerInvoice(@PathVariable String orderId) {
		try {
			return this.orderService.getDesignerInvoice(orderId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/userSideInvoicePDF/{orderId}")
	public String userSideInvoicePDF(@PathVariable String orderId) {
		try {
			return this.orderService.getUserPDFService(orderId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getOrcerCount")
	public Object getOrderCount(@RequestHeader("Authorization") String token) {
		try {
			return this.orderService.getCountOrderService(token);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@PutMapping("/changeorderitemStatus/{orderId}/{status}")
	public GlobalResponce StatusUpdate(@PathVariable String orderId, @PathVariable String status) {
		try {
			return this.orderService.changeitemStatus(orderId, status);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@GetMapping("/getlistItemStatus/{orderItemStatus}/{sortBy}")
	public Object getlistItemStatus(@RequestParam String orderItemStatus,@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,@RequestParam (defaultValue = "false") Boolean isDeleted,
			@RequestParam Optional<String> sortBy) {
		try {
			return orderService.getorderItemStatus(page, limit,sort, isDeleted,sortBy);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
}
