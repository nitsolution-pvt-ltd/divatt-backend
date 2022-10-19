package com.divatt.designer.repo;

import java.time.LocalDate;
import java.util.Date;
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
	
	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'gender' : {$regex:?0,$options:'i'} },{ 'productDescription' : {$regex:?0,$options:'i'} },{ 'productId' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'age.min' : {$regex:?0,$options:'i'} },{ 'designer_name' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1,'designerId' : ?2 }]}")
	Page<ProductMasterEntity> DesignerSearchfindByIsDeletedAndAdminStatusAndDesignerId(String keyword, Boolean isDeleted, String AdminStatus,Integer DesignerId,Pageable pagingSort);
	
	List<ProductMasterEntity> findByIsDeletedAndAdminStatusAndIsActive(Boolean isDeleted, String AdminStatus,Boolean IsActive);
	
	

	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'gender' : {$regex:?0,$options:'i'} },{ 'productDescription' : {$regex:?0,$options:'i'} },{ 'productId' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'designer_name' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1 }]}")
	Page<ProductMasterEntity> Search(String sortkey, Boolean isDeleted, Pageable pagingSort);

	Page<ProductMasterEntity> findByProductIdIn(List<Integer> productIdList, Pageable pagingSort);
	
	List<ProductMasterEntity> findByProductIdIn(List<Integer> productIdList);

    Page<ListProduct> findByDesignerIdIn(List<ListProduct> allList, Pageable pagingSort);
	
	
	Page<ProductMasterEntity> findByIsDeletedAndDesignerId(Boolean isDeleted, Integer designerId,Pageable pagingSort);
	Page<ProductMasterEntity> findByIsDeletedAndDesignerIdAndAdminStatus(Boolean isDeleted, Integer designerId, String adminStatus, Pageable pagingSort);
	
	

	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'gender' : {$regex:?0,$options:'i'} },{ 'productDescription' : {$regex:?0,$options:'i'} },{ 'productId' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'age.min' : {$regex:?0,$options:'i'} },{ 'price.indPrice.mrp' : {$regex:?0,$options:'i'} },{ 'price.usPrice.mrp' : {$regex:?0,$options:'i'} }, { 'designer_name' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1,'designerId' : ?2 }]}")
	Page<ProductMasterEntity> listDesignerProductsearch(String keyword, Boolean isDeleted, Integer designerId,Pageable pagingSort);
	
	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'gender' : {$regex:?0,$options:'i'} },{ 'productDescription' : {$regex:?0,$options:'i'} },{ 'productId' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'age.min' : {$regex:?0,$options:'i'} },{ 'price.indPrice.mrp' : {$regex:?0,$options:'i'} },{ 'price.usPrice.mrp' : {$regex:?0,$options:'i'} }, { 'designer_name' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1,'designerId' : ?2, 'adminStatus' : ?3 }]}")
	Page<ProductMasterEntity> listDesignerProductsearchByAdminStatus(String keyword, Boolean isDeleted, Integer designerId, String adminStatus, Pageable pagingSort);
	
	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'gender' : {$regex:?0,$options:'i'} },{ 'productDescription' : {$regex:?0,$options:'i'} },{ 'productId' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'age.min' : {$regex:?0,$options:'i'} },{ 'price.indPrice.mrp' : {$regex:?0,$options:'i'} },{ 'price.usPrice.mrp' : {$regex:?0,$options:'i'} },{ 'designer_name' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1,'designerId' : ?2 }]}")
	Page<ProductMasterEntity> DesignerSearchfindByIsDeletedAndAdminStatus(String keyword, Boolean isDeleted, String AdminStatus,Pageable pagingSort);
	
	Integer countByIsDeleted(Boolean isDeleted);
	
	Integer countByIsDeletedAndAdminStatus(Boolean isDeleted, String AdminStatus);
	
	Integer countByIsDeletedAndAdminStatusAndDesignerIdAndIsActive(Boolean isDeleted, String AdminStatus,Long DesignerId,Boolean isActive);
	Integer countByIsDeletedAndDesignerIdAndIsActive(Boolean isDeleted, Integer DesignerId, Boolean isActive);
	Integer countByIsDeletedAndDesignerIdAndIsActiveAndAdminStatus(Boolean isDeleted, Integer DesignerId, Boolean isActive, String adminStatus);

	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'productDescription' : {$regex:?0,$options:'i'} },{ 'gender' : {$regex:?0,$options:'i'} },{ 'taxPercentage' : {$regex:?0,$options:'i'} },{ 'price.indPrice.mrp' : {$regex:?0,$options:'i'} },{ 'designerName' : {$regex:?0,$options:'i'} },{ 'price.usPrice.mrp' : {$regex:?0,$options:'i'} } ],$and: [ {  'isDeleted' : ?1, 'adminStatus' : ?2 }]}")
	Page<ProductMasterEntity> SearchAndfindByIsDeletedAndAdminStatus(String keyword,Boolean isDeleted, String AdminStatus, Pageable pagingSort);
	
	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'productDescription' : {$regex:?0,$options:'i'} },{ 'gender' : {$regex:?0,$options:'i'} },{ 'taxPercentage' : {$regex:?0,$options:'i'} },{ 'price.indPrice.mrp' : {$regex:?0,$options:'i'} },{ 'designerName' : {$regex:?0,$options:'i'} },{ 'price.usPrice.mrp' : {$regex:?0,$options:'i'} } ],$and: [ {  'isDeleted' : ?1, 'adminStatus' : ?2}]}")
	Page<ProductMasterEntity> SearchAppAndfindByIsDeletedAndAdminStatus(String keyword,Boolean isDeleted, String AdminStatus, Pageable pagingSort);
	
	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'productDescription' : {$regex:?0,$options:'i'} },{ 'gender' : {$regex:?0,$options:'i'} },{ 'taxPercentage' : {$regex:?0,$options:'i'} },{ 'price.indPrice.mrp' : {$regex:?0,$options:'i'} },{ 'designerName' : {$regex:?0,$options:'i'} },{ 'price.usPrice.mrp' : {$regex:?0,$options:'i'} } ],$and: [ {  'isDeleted' : ?1}]}")
	Page<ProductMasterEntity> SearchAndfindByIsDeleted(String keyword, Boolean isDeleted, Pageable pagingSort);
	
	
//	@Query(value = "{ '$where': 'this.standeredSOH.notify==this.standeredSOH.soh' }")
	@Query(value = "{ standeredSOH : { $gte: notify, $lte: soh}}")
	
//	@Query(value = "{ 'createdOn' : { '$gte': { $regex: ISODate ?0, $options:'i' }} }")
	
//	Page<ProductMasterEntity> findNotify(String currentDate,Pageable pagingSort);
//	Page<ProductMasterEntity> DesignerSearchfindByIsDeletedAndAdminStatus(String keyword, Boolean isDeleted, String AdminStatus,Pageable pagingSort);

	Page<ProductMasterEntity> findNotify(LocalDate date, Pageable pagingSort);

	List<ProductMasterEntity> findByDesignerId(Integer integer);

//	Page<ProductMasterEntity> findByStanderedSOHNotifySohGreaterThan(Pageable pagingSort);

}
