package com.divatt.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;

@Repository
public interface OrderSKUDetailsRepo extends MongoRepository<OrderSKUDetailsEntity, Integer> {

	List<OrderSKUDetailsEntity> findByOrderId(String orderId);

	Page<OrderSKUDetailsEntity> findByDesignerId(int designerId, Pageable pageable);

	List<OrderSKUDetailsEntity> findByDesignerId(int designerId);

	@Query("{'orderId': ?0,'designerId' : ?1}")
	List<OrderSKUDetailsEntity> findByOrderIdAndDesignerId(String orderId, int designerId);

	Optional<OrderSKUDetailsEntity> findByProductId(int productId);

	Page<OrderSKUDetailsEntity> findByOrderItemStatus(Pageable pageable, String orderItemStatus);

	@Query("{ 'orderItemStatus' : ?0}")
	Page<OrderSKUDetailsEntity> Search(String orderItemStatus, Pageable pagingSort);

	@Query("{ 'orderItemStatus' : ?0}")
	List<OrderSKUDetailsEntity> findByOrder(String orderItemStatus);

	@Query("{ 'orderItemStatus' : ?0,'designerId': ?1}")
	Page<OrderDetailsEntity> findByOrderItem(int designerId, String orderIteamStatus, Pageable pagingSort);

	@Query("{'designerId': ?0,'orderItemStatus' : ?1}")
	List<OrderSKUDetailsEntity> findByOrderTotal(int designerId, String orderIteamStatus);

	@Query("{'designerId': ?0,'orderItemStatus' : ?1,'keyword' : {$regex:?0,$options:'i'}}")
	List<OrderSKUDetailsEntity> findByOrderItemStatusAndDesignerIdAndKeyword(int designerId, String orderItemStatus,
			String keyword);

	@Query("{'orderId': ?0,'designerId': ?1,'orderItemStatus' : ?2}")
	List<OrderSKUDetailsEntity> findByOrderIdAndDesignerIdAndorderItemStatus(String orderId, int designerId,
			String orderItemStatus);

	@Query("{'orderId': ?0,'orderItemStatus': ?1}")
	List<OrderSKUDetailsEntity> findByOrderIdAndOrderItemStatus(String orderId, String orderItemStatus);

	@Query("{ 'orderItemStatus' : ?0}")
	List<OrderSKUDetailsEntity> findByOrderItemStatus(String orderItemStatus);

	List<OrderSKUDetailsEntity> findByProductIdAndDesignerIdAndOrderIdAndOrderItemStatus(Integer productId,
			Integer designerId, String orderId, String orderItemStatus);

	List<OrderSKUDetailsEntity> findByProductIdAndDesignerIdAndOrderId(Integer productId, Integer designerId, String orderId);

//	Page<OrderSKUDetailsEntity> findByDesignerId(int designerId, Pageable pagingSort);
	List<OrderSKUDetailsEntity> findByProductIdAndOrderId(Integer parseInt, String orderId);

	@Query("{ 'orderItemStatus' : ?0}")
	Page<OrderSKUDetailsEntity> findOrderStatus(String orderItemStatus, Pageable pagingSort);

	@Query(value = "{ $or: [ { 'order_id' : {$regex:?0,$options:'i'}}, { 'user_id' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} }]}")
	Page<OrderSKUDetailsEntity> Searching(String sortKey, Pageable pageable);

	Page<OrderSKUDetailsEntity> findByOrderIdIn(List<String> collect, Pageable pagingSort);
	
	
	@Query("{'designerId': ?0,'orderItemStatus' : ?1,'orderId' : ?2 }")
	List<OrderSKUDetailsEntity> findByTotalOrder(int designerId, String orderIteamStatus, String orderId);
//	@Query("{'orderId': ?0,'designerId': ?1}")
	List<OrderSKUDetailsEntity> findByDesignerIdAndOrderId(int designerId, String orderId);
	
	List<OrderSKUDetailsEntity> findByProductIdAndOrderIdAndSize(Integer parseInt, String orderId, String size);
	List<OrderSKUDetailsEntity> findByProductIdAndDesignerIdAndOrderIdAndSize(Integer productId, Integer designerId, String orderId,String size);
	
	@Query("{'productId': ?0,'designerId' : ?1,'orderId' : ?2 ,'orderItemStatus' : ?3 ,'size' :?4 }")
	List<OrderSKUDetailsEntity> findByProductIdAndDesignerIdAndOrderIdAndOrderItemStatusAndSize(Integer productId,
			Integer designerId, String orderId, String orderItemStatus, String size);

	@Query("{'user_id': ?0,'productId' : ?1,'designer_id' : ?2 }")
	List<OrderSKUDetailsEntity> findByUserIdAndProductIdAndDesignerId(long l, int productId,
			Integer designerId);
	@Query("{'productId' : ?0,'designer_id' : ?1 }")
	List<OrderSKUDetailsEntity> findByProductIdAndDesignerId(int productId, Integer designerId);
	
	

}
