package com.divatt.designer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.exception.CustomException;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.OrderService;

@RestController
@RequestMapping("/orderStatus")
public class OrderStatusController {
	
	@Autowired
	private OrderService orderService;

	@PutMapping("/orderStatus/{orderId}/{statusKeyword}")
	public GlobalResponce orderStatusUpdate(@PathVariable String orderId ,@PathVariable String statusKeyword)
	{
		try {
			return this.orderService.changeStatus(orderId,statusKeyword);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
}
