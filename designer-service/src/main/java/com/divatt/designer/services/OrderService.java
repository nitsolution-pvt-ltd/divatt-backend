package com.divatt.designer.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.divatt.designer.entity.OrderDetailsEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.response.GlobalResponce;

@Service
public class OrderService {

	public GlobalResponce changeStatus(String orderId, String statusKeyword) {
		try {
			RestTemplate restTemplate= new RestTemplate();
			ResponseEntity<OrderDetailsEntity> serviceResponse= restTemplate.getForEntity("http://localhost:8082/dev/userOrder/getOrder/"+orderId, OrderDetailsEntity.class);
			//System.out.println(serviceResponse.getBody());
			OrderDetailsEntity updatedOrder=serviceResponse.getBody();
			updatedOrder.setOrderStatus(statusKeyword);
			System.out.println(updatedOrder);
			restTemplate.put("Http://localhost:8082/dev/userOrder/updateOrder/" + orderId,updatedOrder ,
					String.class);
			return new GlobalResponce("Success", "Order status updated", 200);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
