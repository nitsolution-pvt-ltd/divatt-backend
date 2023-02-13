package com.divatt.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.user.entity.order.OrderDetailsEntity;

public interface OrderDetailsRepo extends MongoRepository<OrderDetailsEntity, Long>{
	
	@Query(value = "{ $or: [ { 'order_id' : {$regex:?0,$options:'i'}}, { 'user_id' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} }]}")
//	 @Query(value = "{ 'products.productName' : {$all : [?0] }}")
	Page<OrderDetailsEntity> Search(String sortKey, Pageable pageable);
	
	
	@Query("{ 'orderId' : ?0}")
	List<OrderDetailsEntity> findByOrderId(String orderId);	
	
	@Query("{ 'orderId' : ?0}")
	Optional<OrderDetailsEntity> findByOrderIds(int page, int limit, String orderId, Boolean isDeleted, Optional<String> sortBy);
	
	List<OrderDetailsEntity> findByUserIdOrderByIdDesc(Integer UserId);
	
	@Query(value = "{ 'products': { $elemMatch: { 'designerId' : ?0 } }}")
	Page<OrderDetailsEntity> findDesigner(Integer designerId, Pageable pagingSort);

	@Query("{ 'orderId' : ?0}")
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


	

    @Query("{ 'orderId' : ?0}")
    Page<OrderDetailsEntity> findOrderId(String orderId,Pageable pagingSort);


    @Query("{ 'order_status' : ?0}")
	Page<OrderDetailsEntity> findOrderStatus(String orderStatus, Pageable pagingSort);
	
//	Page<OrderDetailsEntity> findByDesignerId(int designerId,Pageable pageable);
  
    //@Query(value = "{ $or: [  { 'user_id' : {$regex:?0,$options:'i'} }]}")
    @Query("{ 'userId' : ?0}")
	Page<OrderDetailsEntity> findByUserId(Integer userId, Pageable pageable);
   
    //@Query(value = "{ $or: [ { 'orderId' : {$regex:?0,$options:'i'} },{ 'mrp' : {$regex:?0,$options:'i'} },{ 'deliveryStatus' : {$regex:?0,$options:'i'} },{ 'orderStatus' : {$regex:?0,$options:'i'} },{ 'deliveryMode' : {$regex:?0,$options:'i'} } ],$and: [ {  'discount' : ?1}]}")
    @Query("{ 'userId' : ?0,}")
    Page<OrderDetailsEntity> findByUserIdAndKeyword(Integer userId,String keyword, Pageable pageable);
    @Query("{ 'orderId' : ?0}")
	OrderDetailsEntity findByOrderIds(String orderId);	
    
    
}