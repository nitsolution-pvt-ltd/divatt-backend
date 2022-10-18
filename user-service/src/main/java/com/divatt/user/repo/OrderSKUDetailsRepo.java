package com.divatt.user.repo;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.user.entity.order.OrderSKUDetailsEntity;

public interface OrderSKUDetailsRepo extends MongoRepository<OrderSKUDetailsEntity,Integer>{
	
	
	List<OrderSKUDetailsEntity> findByOrderId(String orderId);
	
	Page<OrderSKUDetailsEntity> findByDesignerId(int designerId,Pageable pageable);
	
	List<OrderSKUDetailsEntity> findByDesignerId(int designerId);
	
	List<OrderSKUDetailsEntity> findByOrderIdAndDesignerId(String orderId,int designerId);
	
	Optional<OrderSKUDetailsEntity> findByProductId(int productId);
	 
	

	Page<OrderSKUDetailsEntity> findByOrderItemStatus(Pageable pageable,String orderItemStatus);
	@Query("{ 'orderItemStatus' : ?0}")
	Page<OrderSKUDetailsEntity> Search(String orderItemStatus,  Pageable pagingSort);
	@Query("{ 'orderItemStatus' : ?0}")
	List<OrderSKUDetailsEntity> findByOrder(String orderItemStatus);
	
	

//	Page<OrderSKUDetailsEntity> findByDesignerId(int designerId, Pageable pagingSort);

}
