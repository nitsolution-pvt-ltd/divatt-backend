package com.divatt.user.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;

public interface OrderDetailsRepo extends MongoRepository<OrderDetailsEntity, Long>{
	
	@Query(value = "{ $or: [ { 'order_id' : {$regex:?0,$options:'i'}}, { 'user_id' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} }]}")
//	 @Query(value = "{ 'products.productName' : {$all : [?0] }}")
	Page<OrderDetailsEntity> Search(String sortKey, Pageable pageable);
	
	
	
	List<OrderDetailsEntity> findByOrderId(String orderId);	
	
	@Query("{ 'orderId' : ?0}")
	Optional<OrderDetailsEntity> findByOrderIds(String orderId);
	
	List<OrderDetailsEntity> findByUserIdOrderByIdDesc(Integer UserId);
	
	@Query(value = "{ 'products': { $elemMatch: { 'designerId' : ?0 } }}")
	Page<OrderDetailsEntity> findDesigner(Integer designerId, Pageable pagingSort);

	
	Page<OrderDetailsEntity> findByOrderId(String orderId, Pageable pagingSort);

	@Query(value = "{ $or: [ { 'user_id' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} }],$and: [ {  'order_id' : ?1}]}")
	Page<OrderDetailsEntity> SearchByOrderId( String keyword, String orderId, Pageable pagingSort);

	@Query("{ 'orderItemStatus' : ?0}")
    Page<OrderDetailsEntity> SearchorderItemStatus(String orderItemStatus,  Pageable pagingSort);

	OrderDetailsEntity findTopByOrderByIdDesc();


	Page<OrderDetailsEntity> findByOrderIdIn(List<String> orderId, Pageable pagingSort);


	@Query(value = "{ 'razorpay_order_id' : {$regex:?0,$options:'i'} }")
	List<OrderDetailsEntity> findByRazorpayOrderId(String string);


	List<OrderDetailsEntity> findByOrderIdIn(List<String> orderIdList);


	@Query("{ 'orderItemStatus' : ?0}")
    List<OrderDetailsEntity> findByOrder(String orderItemStatus);
	
//	Page<OrderDetailsEntity> findByDesignerId(int designerId,Pageable pageable);
}