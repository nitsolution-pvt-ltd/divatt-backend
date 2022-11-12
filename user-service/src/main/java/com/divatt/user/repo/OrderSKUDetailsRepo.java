package com.divatt.user.repo;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;

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

	List<OrderSKUDetailsEntity> findByProductIdAndDesignerIdAndOrderId(Integer parseInt, Integer parseInt2, String orderId);

//	Page<OrderSKUDetailsEntity> findByDesignerId(int designerId, Pageable pagingSort);

}
