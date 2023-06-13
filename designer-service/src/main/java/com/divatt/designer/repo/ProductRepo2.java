package com.divatt.designer.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.designer.entity.product.ProductMasterEntity;
import com.divatt.designer.entity.product.ProductMasterEntity2;

@Repository
public interface ProductRepo2 extends MongoRepository<ProductMasterEntity2, Integer> {

	Integer countByIsDeleted(Boolean isDeleted);

	Integer countByIsDeletedAndAdminStatus(Boolean isDeleted, String adminStatus);

	Integer countByIsDeletedAndAdminStatusAndDesignerIdAndIsActive(Boolean isDeleted, String adminStatus,
			Integer designerId, Boolean isActive);

	Page<ProductMasterEntity2> findByIsDeleted(Boolean isDeleted, Pageable pagingSort);

	Page<ProductMasterEntity2> findByIsDeletedAndAdminStatus(Boolean isDeleted, String adminStatus,
			Pageable pagingSort);

	@Query(value = "{ $or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName,' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} },{ 'mrp' : {$regex:?0,$options:'i'} } ],$and: [ {  'isDeleted' : ?1}]}")
	Page<ProductMasterEntity2> SearchAndfindByIsDeleted(String keyword, Boolean isDeleted, Pageable pagingSort);

	@Query(value = "{ $or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} },{ 'mrp' : {$regex:?0,$options:'i'} } ],$and: [ {  'isDeleted' : ?1, 'adminStatus' : ?2 }]}")
	Page<ProductMasterEntity2> SearchAndfindByIsDeletedAndAdminStatus(String keyword, Boolean isDeleted,
			String adminStatus, Pageable pagingSort);

	@Query(value = "{ $or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} },{ 'mrp' : {$regex:?0,$options:'i'} } ],$and: [ {  'isDeleted' : ?1, 'adminStatus' : ?2}]}")
	Page<ProductMasterEntity2> SearchAppAndfindByIsDeletedAndAdminStatus(String keyword, Boolean isDeleted,
			String adminStatus, Pageable pagingSort);

	@Query(value = "{ $or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} },{ 'mrp' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1 }]}")
	Page<ProductMasterEntity2> findByKeyword(String keyword, Boolean isDeleted, Pageable pageable);

	Page<ProductMasterEntity2> findByDesignerIdAndAdminStatus(Boolean isDeleted, Integer designerId, String adminStatus,
			Boolean isActive, Pageable pagingSort);

	Integer countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(Boolean isDeleted, Integer designerId,
			Boolean isActive, String string);

	Page<ProductMasterEntity2> findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(Boolean isDeleted,
			Integer designerId, String adminStatus, Boolean isActive, Pageable pagingSort);

	/***Pending and rejected data Search***/
	@Query(value = "{ $or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'productDetails.composition' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1,'designerId' : ?2, 'adminStatus' : ?3 }]}")
	Page<ProductMasterEntity2> listDesignerProductsearchByAdminStatus(String keyword, Boolean isDeleted,
			Integer designerId, String adminStatus, Pageable pagingSort);

	@Query(value = "{ $or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'productDetails.composition' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1,'designerId' : ?2, 'adminStatus' : ?3, 'isActive' : ?4 }]}")
	Page<ProductMasterEntity2> listDesignerProductsearchByAdminStatusForOos(String keyword, Boolean isDeleted,
			Integer designerId, String adminStatus, Boolean isActive, Pageable pagingSort);

	List<ProductMasterEntity2> findByIsDeletedAndAdminStatusAndIsActive(Boolean isDeleted, String adminStatus,
			Boolean isActive);
	
	List<ProductMasterEntity2> findByIsDeletedAndAdminStatusAndIsActiveAndDesignerId(Boolean isDeleted, String adminStatus,
			Boolean isActive, Long did);
	
	List<ProductMasterEntity2> findByIsDeletedAndAdminStatusAndIsActiveAndDesignerIdAndProductId(Boolean isDeleted, String adminStatus,
			Boolean isActive, Long did, Integer productId);
	
	List<ProductMasterEntity2> findByIsDeletedAndIsActiveAndDesignerIdAndProductId(Boolean isDeleted,
			Boolean isActive, Long did, Integer productId);

	Page<ProductMasterEntity2> findByProductIdIn(List<Integer> productIdList, Pageable pagingSort);

	List<ProductMasterEntity2> findByProductIdIn(List<Integer> productIdList);

	@Query(value = "{$or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'productDetails.productDescription' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} }, { 'colour' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'productDetails.composition' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} } ]"
			+ "$and: [{'designerId':?1}]}")
	List<ProductMasterEntity2> findbySearchKeyAndDesignerId(String searchKey, Long dID);

	List<ProductMasterEntity2> findByIsDeletedAndDesignerIdAndAdminStatusAndIsActive(Boolean isDeleted,
			Integer designerId, String adminStatus, Boolean isActive);

	List<ProductMasterEntity2> findByDesignerIdAndIsDeletedAndAdminStatusAndIsActive(Integer designerId,
			Boolean isDeleted, String adminStatus, Boolean isActive);

	@Query(value = "{$or: [ { 'filter.soh==filter.notify' : {$regex:?0,$options:'i'} }, { 'filter.soh<=filter.notify' : {$regex:?0,$options:'i'} } } ]}")
	Page<ProductMasterEntity2> findProduct(List<ProductMasterEntity2> filter, Pageable pagingSort);

	/***Live data Search***/
	@Query(value = "{$or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'productDetails.composition' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} } ]"
			+ "$and: [{'is_deleted': ?1,'designerId':?2,'adminStatus':?3,'is_active':?4}] }")
	Page<ProductMasterEntity2> searckLiveByKeyword(String keyword, Boolean isDeleted, Integer designerId, String adminStatus, Boolean isActive, Pageable pagingSort);
	
	/***Low stock data Search***/
	@Query(value = "{$or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'productDetails.composition' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} } ]"
			+ "$and: [{'is_deleted': ?1,'designerId':?2,'adminStatus':?3,'is_active':?4}] }")
	Page<ProductMasterEntity2> searckLSByKeyword(String keyword, Boolean isDeleted, Integer designerId, String adminStatus, Boolean isActive, Pageable pagingSort);

	@Query(value = "{$or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'productDetails.composition' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} } ]"
			+ "$and: [{'is_deleted': ?1,'designerId':?2,'adminStatus':?3,'is_active':?4}] }")
	Page<ProductMasterEntity2> searckOosByKeyword(String keyword, Boolean isDeleted, Integer designerId, String adminStatus, Boolean isActive, Pageable pagingSort);
	
	
	
	
	
	List<ProductMasterEntity2> findByCategoryId(Integer categoryId);
	@Query(value = "{$or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'productDetails.composition' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} } ] }")
	Page<ProductMasterEntity2> findByIsDeletedAndSearch(String keyword, Boolean isDeleted, Pageable pagingSort);

	List<ProductMasterEntity2> findByDesignerId(Integer designerId);
	
	Integer countByIsDeletedAndAdminStatusAndDesignerIdAndIsActiveAndSohNot(Boolean isDeleted, String adminStatus,
			Integer designerId, Boolean isActive, Integer soh);
//
	List<ProductMasterEntity2> findByDesignerId(Long designerId);
	@Query(value = "{$or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'productDetails.productDescription' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} }, { 'colour' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'productDetails.composition' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} } ]"
		+ "$and: [{'designerName':?1}]}")
	List<ProductMasterEntity2> findbySearchKeyAndDesignerName(String searchKey, String designerName);
	@Query(value = "{$or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'productDetails.productDescription' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} }, { 'colour' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'productDetails.composition' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} } ]}")
			
	List<ProductMasterEntity2>  findbySearchKey(String searchKey);
	List<ProductMasterEntity2> findByIsActive(Boolean isDeleted);
	
	List<ProductMasterEntity2> findByIsDeletedAndDesignerIdAndAdminStatus(Boolean isDeleted,
			Integer designerId, String adminStatus);
	Page<ProductMasterEntity2> findByIsDeletedAndDesignerIdAndAdminStatus(Boolean isDeleted,
			Integer designerId, String adminStatus, Pageable pagingSort);
	@Query(value = "{$or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'productDetails.composition' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} }, { 'mrp' : {$regex:?0,$options:'i'} } ]"
			+ "$and: [{'is_deleted': ?1,'designerId':?2,'adminStatus':?3}] }")
	Page<ProductMasterEntity2> searcOosByKeyword(String keyword, Boolean isDeleted, Integer designerId, String adminStatus, Pageable pagingSort);


	Optional<ProductMasterEntity2> findByProductId(Integer productId);
	


	

	
	
}
