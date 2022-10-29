package com.divatt.designer.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.designer.entity.product.ProductMasterEntity2;

@Repository
public interface ProductRepo2 extends MongoRepository<ProductMasterEntity2, Integer> {

    Integer countByIsDeleted(Boolean isDeleted);

    Integer countByIsDeletedAndAdminStatus(Boolean isDeleted, String string);

    Page<ProductMasterEntity2> findByIsDeleted(Boolean isDeleted, Pageable pagingSort);

    Page<ProductMasterEntity2> findByIsDeletedAndAdminStatus(Boolean isDeleted, String adminStatus,
            Pageable pagingSort);

    @Query(value = "{ $or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName,' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} },{ 'mrp' : {$regex:?0,$options:'i'} } ],$and: [ {  'isDeleted' : ?1}]}")
    Page<ProductMasterEntity2> SearchAndfindByIsDeleted(String keyword, Boolean isDeleted, Pageable pagingSort);

    @Query(value = "{ $or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} },{ 'mrp' : {$regex:?0,$options:'i'} } ],$and: [ {  'isDeleted' : ?1, 'adminStatus' : ?2 }]}")
    Page<ProductMasterEntity2> SearchAndfindByIsDeletedAndAdminStatus(String keyword, Boolean isDeleted,
            String adminStatus,
            Pageable pagingSort);

    @Query(value = "{ $or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} },{ 'mrp' : {$regex:?0,$options:'i'} } ],$and: [ {  'isDeleted' : ?1, 'adminStatus' : ?2}]}")
    Page<ProductMasterEntity2> SearchAppAndfindByIsDeletedAndAdminStatus(String keyword, Boolean isDeleted,
            String adminStatus, Pageable pagingSort);

    @Query(value = "{ $or: [ { 'productStage' : {$regex:?0,$options:'i'} }, { 'productDetails.occation' : {$regex:?0,$options:'i'} },{ 'Deal.dealName' : {$regex:?0,$options:'i'} },{ 'colour' : {$regex:?0,$options:'i'} },{ 'productDetails.productName' : {$regex:?0,$options:'i'} },{ 'priceType' : {$regex:?0,$options:'i'} },{ 'mrp' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1 }]}")
    Page<ProductMasterEntity2> findByKeyword(String keyword, Boolean isDeleted, Pageable pageable);

}
