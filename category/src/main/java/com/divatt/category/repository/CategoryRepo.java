package com.divatt.category.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.category.entity.CategoryEntity;


@Repository
public interface CategoryRepo extends MongoRepository<CategoryEntity, Integer>{

	Optional<CategoryEntity> findByCategoryName(String categoryName);
	
	Page<CategoryEntity> findByIsDeleted(Boolean isDeleted,Pageable pagingSort);
	
//	@Query("{ 'categoryName' : ?0, 'isDeleted' : ?1 }")
	@Query(value = "{ $or: [ { 'categoryName' : {$regex:?0,$options:'i'} }, { 'categoryDescrition' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'categoryImage' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} } ],$and: [ { 'isDeleted' : ?1 }]}")
    Page<CategoryEntity> Search(String sortKey, Boolean isDeleted,Pageable pageable);
}