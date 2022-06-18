package com.divatt.user.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.user.entity.UserLoginEntity;



public interface UserLoginRepo extends MongoRepository<UserLoginEntity, Long>{
	Optional<UserLoginEntity> findByEmail(String email);

	@Query(value = "{ $or: [ { 'firstName' : {$regex:?0,$options:'i'} }, { 'lastName' : {$regex:?0,$options:'i'} },{ 'email' : {$regex:?0,$options:'i'} },{ 'mobileNo' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} } ] ,$and: [ { 'isDeleted' : ?1 }]}")
	Page<UserLoginEntity> Search(String keyword, Boolean isDeleted, Pageable pagingSort);

	Page<UserLoginEntity> findByIsDeleted(Boolean isDeleted, Pageable pagingSort);

//	@Query(value = "{ $or: [ { 'productName' : {$regex:?0,$options:'i'} }, { 'gender' : {$regex:?0,$options:'i'} },{ 'productDescription' : {$regex:?0,$options:'i'} },{ 'productId' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} },{ 'age.min' : {$regex:?0,$options:'i'} },{ 'designer_name' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDeleted' : ?1,'designerId' : ?2 }]}")
	
}
