package com.divatt.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.order.OrderSKUDetailsEntity;

public interface OrderSKUDetailsRepo extends MongoRepository<OrderSKUDetailsEntity,Integer>{
	
	List<OrderSKUDetailsEntity> findByOrderId(String orderId);

}