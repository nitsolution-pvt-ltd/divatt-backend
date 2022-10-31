package com.divatt.designer.services;


import org.springframework.http.ResponseEntity;

import com.divatt.designer.response.GlobalResponce;

public interface OrderService {
	
	public GlobalResponce changeStatus(String orderId, String statusKeyword);

	public ResponseEntity<?> getDesignerInvoice(String orderId);

	public String getUserPDFService(String orderId);

	public Object getCountOrderService(String token);

	public GlobalResponce changeitemStatus(String orderId, String status);

}
