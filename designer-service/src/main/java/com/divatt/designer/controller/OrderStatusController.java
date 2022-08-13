package com.divatt.designer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.divatt.designer.entity.ProductInvoice;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.OrderService;

@RestController
@RequestMapping("/order")
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
	
	@GetMapping("/genrateDesignerInvoice/{orderId}")
	public ResponseEntity<?> genarateDesignerInvoice(@PathVariable String orderId) {
		try {
			return this.orderService.getDesignerInvoice(orderId);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
