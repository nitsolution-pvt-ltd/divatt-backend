package com.divatt.admin.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.category.CategoryEntity;



@Repository
public interface CategoryRepo extends MongoRepository<CategoryEntity, Integer>{

	Optional<CategoryEntity> findByCategoryName(String categoryName);	
	
	Optional<CategoryEntity> findByIsDeletedAndParentId(Boolean falses, Integer ParentId);	
	
	@Query("{ 'isDeleted' : ?0, 'parentId' : ?1 }")	
	List<CategoryEntity> VerifyCategory(Boolean falses, String ParentId);	
	
	Page<CategoryEntity> findByIsDeletedAndParentId(Boolean isDeleted,String parentId,Pageable pagingSort);
	
//	@Query("{ 'categoryName' : ?0, 'isDeleted' : ?1 }")
	@Query(value = "{ $or: [ { 'categoryName' : {$regex:?0,$options:'i'} }, { 'categoryDescription' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'categoryImage' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1 }],$and: [ { 'parentId' : ?2 }]}")
    Page<CategoryEntity> Search(String sortKey, Boolean isDeleted,String parentId,Pageable pageable);

	List<CategoryEntity> findByIsDeletedAndIsActiveAndParentId(Boolean isDeleted, Boolean Status,String string);

//	Optional<CategoryEntity> findByIsDeletedAndParentId(String falses, String string);
}
