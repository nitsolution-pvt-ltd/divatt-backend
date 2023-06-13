package com.divatt.admin.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.AccountEntity;

@Repository
public interface AccountRepo extends MongoRepository<AccountEntity, Long>{

	Page<AccountEntity> findById(long i, Pageable pagingSort);
	
	@Query(value = "{ 'service_charge.date': ?0 }")
	Page<AccountEntity> findByServiceChargeDate(long i, Pageable pagingSort);
	
	@Query(value = "{ 'designer_details.designer_id': ?0 }")
	Page<AccountEntity> findByDesignerId(long i, Pageable pagingSort);

//	@Query(value = "{ 'designer_details': { $elemMatch: { 'designer_id' : ?0 } }}")
//	Page<AccountEntity> findByDesignerId(long i, Pageable pagingSort);

	@Query(" {$or :[{'admin_details.admin_id': {$regex:?0,$options:'i'} }, {'admin_details.name': {$regex:?0,$options:'i'} } ]}")
	Page<AccountEntity> AccountSearchByKeywords(String pattern, Pageable pagingSort);

	@Query("{$or :[{'order_details': { $elemMatch: {'order_id':'?0'} }},{'order_details': { $elemMatch: {'invoice_id':'?1'} }}]}")
	List<AccountEntity> findByOrderIdAndInvoiceId(String order_id, String invoice_id);

	Page<AccountEntity> findAllByOrderByIdDesc(Pageable pagingSort);

	@Query(value = "{ '_id': ?0 }")
	List<AccountEntity> findById(long accountId);
	
	@Query(value = "{ '_id': ?0 }")
	Optional<AccountEntity> findByAccountId(long accountId);

	@Query("{$or :[{'designer_return_amount': { $elemMatch: {'designer_id':'?0'} }},{'designer_return_amount': { $elemMatch: {'order_id':'?1'} }},{'designer_return_amount': { $elemMatch: {'product_id':'?2'} }}]}")
	List<AccountEntity> findByDesignerIdAndOrderIdAndProductId(Long designer_id, String order_id, int product_id);
	@Query("{$or :[{'designer_return_amount': { $elemMatch: {'razorpayXPaymentId':'?0'} }}]}")
	List<AccountEntity> findByRazorpayXPaymentId(String razorpayXPaymentId);

}
