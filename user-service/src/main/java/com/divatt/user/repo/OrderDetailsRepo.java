package com.divatt.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;

public interface OrderDetailsRepo extends MongoRepository<OrderDetailsEntity, Long>{
	@Query(value = "{ $or: [ { 'order_id' : {$regex:?0,$options:'i'} }, { 'user_id' : {$regex:?0,$options:'i'} } ]}")
	Page<OrderDetailsEntity> Search(String sortKey, Pageable pageable);
	
	
	Optional<OrderDetailsEntity> findByOrderId(String orderId);
	
	List<OrderDetailsEntity> findByUserId(Integer orderId);
	
	
	
}
