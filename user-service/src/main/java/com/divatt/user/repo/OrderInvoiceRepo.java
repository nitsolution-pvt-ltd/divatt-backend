package com.divatt.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.user.entity.OrderInvoiceEntity;

public interface OrderInvoiceRepo extends MongoRepository<OrderInvoiceEntity, Long> {

	List<OrderInvoiceEntity> findByInvoiceId(String inoviceId);

	@Query(value = "{ $or: [ { 'invoice_id' : {$regex:?0,$options:'i'} }, { 'invoice_datetime' : {$regex:?0,$options:'i'} },{ 'order_id' : {$regex:?0,$options:'i'} },{ 'order_datetime' : {$regex:?0,$options:'i'} },{ 'designer_details.GSTIN' : {$regex:?0,$options:'i'} },{ 'designer_details.PAN' : {$regex:?0,$options:'i'} },{ 'designer_details.name' : {$regex:?0,$options:'i'} },{ 'designer_details.mobile' : {$regex:?0,$options:'i'} },{ 'user_details.name' : {$regex:?0,$options:'i'} },{ 'user_details.mobile' : {$regex:?0,$options:'i'}} ]}")
	Page<OrderInvoiceEntity> SearchByKey(String keyword, Pageable pagingSort);

	OrderInvoiceEntity findTopByOrderByIdDesc();

	Optional<OrderInvoiceEntity> findByOrderId(String orderId);
	@Query("{ 'orderId' : ?0}")
	List<OrderInvoiceEntity> findByOrder(String orderId);

}
