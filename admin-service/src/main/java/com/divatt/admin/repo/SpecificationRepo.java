package com.divatt.admin.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.product.SpecificationEntity;

@Repository
public interface SpecificationRepo extends MongoRepository<SpecificationEntity, Integer>{

	Optional<SpecificationEntity> findByName(String name);

	Page<SpecificationEntity> findByIsDeleted(Boolean isDeleted, Pageable pagingSort);
	
	@Query(value = "{ $or: [ { 'categoryName' : {$regex:?0,$options:'i'} }, { 'name' : {$regex:?0,$options:'i'} },{ 'type' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1 }]}")
	Page<SpecificationEntity> Search(String keyword, Boolean isDeleted, Pageable pagingSort);
	
	
	

}
