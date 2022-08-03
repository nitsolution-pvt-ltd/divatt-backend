package com.divatt.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.user.entity.OrderTrackingEntity;

public interface OrderTrackingRepo extends MongoRepository<OrderTrackingEntity,Integer>{

	List<OrderTrackingEntity> findByOrderId(String orderId);
	
	@Query("{ 'order_id' : ?0}")	
//	@Query(value = "{ $or: [ { 'order_id' : {$regex:?0,$options:'i'} }, { 'userId' : {$regex:?0,$options:'i'} },{ 'designerId' : {$regex:?0,$options:'i'} }]}")
	List<OrderTrackingEntity> findByOrderIdL(String orderId);
	
	@Query("{ 'order_id' : ?0, 'user_id' : ?1, 'designer_id' : ?2}")	
	List<OrderTrackingEntity> findByOrderIdLDU(String orderId,int user_id,int designer_id);
	
	Optional<OrderTrackingEntity> findByTrackingId(String trackingId);
	
	
	@Query("{ 'tracking_id' : ?0}")	
	List<OrderTrackingEntity> findByTrackingIds(String trackingId);
	
	List<OrderTrackingEntity> findByTrackingIdAndDesignerId(String trackingId);
	
	
}
 