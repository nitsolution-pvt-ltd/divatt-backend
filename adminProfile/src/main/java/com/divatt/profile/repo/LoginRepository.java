package com.divatt.profile.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import com.divatt.profile.entity.LoginEntity;





@Repository
public interface LoginRepository extends MongoRepository<LoginEntity, Object> {

//	Optional<LoginEntity> findByUserName(String userName);

	public Optional<LoginEntity> findByEmail(String email);
	
Page<LoginEntity> findByIsDeleted(Boolean isDeleted,Pageable pagingSort);
	
	@Query(value = "{ $or: [ { 'first_name' : {$regex:?0,$options:'i'} }, { 'last_name' : {$regex:?0,$options:'i'} },{ 'email' : {$regex:?0,$options:'i'} },{ 'mobile_no' : {$regex:?0,$options:'i'} },{ 'created_on' : {$regex:?0,$options:'i'} } ],$and: [ { 'isDeleted' : ?1 }]}")
    Page<LoginEntity> Search(String sortKey, Boolean isDeleted,Pageable pageable);
	
	
	
//	@Query(value = "select login from LoginEntity login where userName = ?1 and accessToken = ?2")
//	Optional<LoginEntity> findByUserNameAndToken(String userName,String accessToken);
	

}
