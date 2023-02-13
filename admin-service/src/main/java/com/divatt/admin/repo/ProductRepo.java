package com.divatt.admin.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.product.ProductEntity;


@Repository
public interface ProductRepo extends MongoRepository<ProductEntity, Integer> {
 @Query("{'isDeleted':?0 ,'isActive':?1}")
Page<ProductEntity> findByStatus(Boolean isDeleted,Boolean isActive,Pageable pagingSort);
	
 @Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'gender' : {$regex:?0,$options:'i'} },{ 'productDescription' : {$regex:?0,$options:'i'} },{ 'productId' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'designer_name' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1 }]}")	
 Page<ProductEntity> Search(String keyword, Boolean isDeleted, String string, Pageable pagingSort);
 @Query("{'isDeleted':?0}")
Page<ProductEntity> findByIsDelete(boolean isDeleted, Pageable pagingSort);
}
