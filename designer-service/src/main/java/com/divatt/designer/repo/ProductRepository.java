package com.divatt.designer.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.designer.entity.ListProduct;
import com.divatt.designer.entity.product.ProductMasterEntity;
@Repository
public interface ProductRepository extends MongoRepository<ProductMasterEntity, Integer>{

	Optional<ProductMasterEntity> findByProductName(String productName);
	
	Optional<ProductMasterEntity>findById(Integer productId);
	
	@Query(value = "{ $or: [ { 'productId' : {$regex:?0,$options:'i'} } ] }")
	List<ProductMasterEntity>findProductData(Integer productId);
	
	Page<ProductMasterEntity> findByIsDeleted(Boolean isDeleted, Pageable pagingSort);
	
	Page<ProductMasterEntity> findByIsDeletedAndAdminStatus(Boolean isDeleted, String AdminStatus, Pageable pagingSort);
	
	List<ProductMasterEntity> findByDesignerIdAndIsDeletedAndAdminStatusAndIsActive(Integer designerId,Boolean isDeleted, String AdminStatus, Boolean isActive);
	
	
	Page<ProductMasterEntity> findByIsDeletedAndAdminStatusAndDesignerId(Boolean isDeleted, String AdminStatus, Integer DesignerId, Pageable pagingSort);
	
	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'gender' : {$regex:?0,$options:'i'} },{ 'productDescription' : {$regex:?0,$options:'i'} },{ 'productId' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'age.min' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1,'designerId' : ?2 }]}")
	Page<ProductMasterEntity> DesignerSearchfindByIsDeletedAndAdminStatusAndDesignerId(String keyword, Boolean isDeleted, String AdminStatus,Integer DesignerId,Pageable pagingSort);
	
	List<ProductMasterEntity> findByIsDeletedAndAdminStatusAndIsActive(Boolean isDeleted, String AdminStatus,Boolean IsActive);
	
	

	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'gender' : {$regex:?0,$options:'i'} },{ 'productDescription' : {$regex:?0,$options:'i'} },{ 'productId' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1 }]}")
	Page<ProductMasterEntity> Search(String sortkey, Boolean isDeleted, Pageable pagingSort);

	Page<ProductMasterEntity> findByProductIdIn(List<Integer> productIdList, Pageable pagingSort);
	
	List<ProductMasterEntity> findByProductIdIn(List<Integer> productIdList);

    Page<ListProduct> findByDesignerIdIn(List<ListProduct> allList, Pageable pagingSort);
	
	
	Page<ProductMasterEntity> findByIsDeletedAndDesignerId(Boolean isDeleted, Integer designerId,Pageable pagingSort);
	
	

	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'gender' : {$regex:?0,$options:'i'} },{ 'productDescription' : {$regex:?0,$options:'i'} },{ 'productId' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'age.min' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1,'designerId' : ?2 }]}")
	Page<ProductMasterEntity> listDesignerProductsearch(String keyword, Boolean isDeleted, Integer designerId,Pageable pagingSort);
	
	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'gender' : {$regex:?0,$options:'i'} },{ 'productDescription' : {$regex:?0,$options:'i'} },{ 'productId' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'age.min' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1,'designerId' : ?2 }]}")
	Page<ProductMasterEntity> DesignerSearchfindByIsDeletedAndAdminStatus(String keyword, Boolean isDeleted, String AdminStatus,Pageable pagingSort);
	
	Integer countByIsDeleted(Boolean isDeleted);
	
	Integer countByIsDeletedAndAdminStatus(Boolean isDeleted, String AdminStatus);
	
	Integer countByIsDeletedAndAdminStatusAndDesignerId(Boolean isDeleted, String AdminStatus,Long DesignerId);
	
//	Integer countByIsDeletedAndAdminAdApproved(Boolean isDeleted, String AdminAdApproved);	
	
//	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'productDescription' : {$regex:?0,$options:'i'} },{ 'gender' : {$regex:?0,$options:'i'} },{ 'taxPercentage' : {$regex:?0,$options:'i'} },{ 'price.indPrice.dealPrice' : {$regex:?0,$options:'i'} } ],$and: [ {  'isDeleted' : ?1, 'adminStatus' : ?2, 'isActive' : ?3 }]}")
//	Page<ProductMasterEntity> SearchAndfindByIsDeletedAndAdminStatusAndIsActive(String keyword,Boolean isDeleted, String AdminStatus, Boolean isActive, Pageable pagingSort);
	
	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'productDescription' : {$regex:?0,$options:'i'} },{ 'gender' : {$regex:?0,$options:'i'} },{ 'taxPercentage' : {$regex:?0,$options:'i'} },{ 'price.indPrice.dealPrice' : {$regex:?0,$options:'i'} } ],$and: [ {  'isDeleted' : ?1, 'adminStatus' : ?2 }]}")
	Page<ProductMasterEntity> SearchAndfindByIsDeletedAndAdminStatus(String keyword,Boolean isDeleted, String AdminStatus, Pageable pagingSort);
	
	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'productDescription' : {$regex:?0,$options:'i'} },{ 'gender' : {$regex:?0,$options:'i'} },{ 'taxPercentage' : {$regex:?0,$options:'i'} },{ 'price.indPrice.dealPrice' : {$regex:?0,$options:'i'} } ],$and: [ {  'isDeleted' : ?1, 'adminStatus' : ?2}]}")
	Page<ProductMasterEntity> SearchAppAndfindByIsDeletedAndAdminStatus(String keyword,Boolean isDeleted, String AdminStatus, Pageable pagingSort);

	

}
