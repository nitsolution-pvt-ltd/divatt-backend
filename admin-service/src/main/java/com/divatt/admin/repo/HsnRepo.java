package com.divatt.admin.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.hsnCode.HsnEntity;


@Repository
public interface HsnRepo extends MongoRepository<HsnEntity, Integer>{
	@Query("{'hsnCode': ?0}")
	Optional<HsnEntity> findByhsnCode(Integer hsnCode);
	
	@Query("{'hsnCode': ?0}")
	HsnEntity findByHsn(Integer hsnCode);
	
	@Query("{'isDelete': ?0}")
	Page<HsnEntity> findByIsDelete(Boolean isDelete, String string, Pageable pagingSort);

	
	@Query(value = "{ $or: [ { 'id' : {$regex:?0,$options:'i'} }, { 'hsnCode' : {$regex:?0,$options:'i'} },{ 'sgst' : {$regex:?0,$options:'i'} },{ 'cgst' : {$regex:?0,$options:'i'} },{ 'igst' : {$regex:?0,$options:'i'} },{ 'taxValue' : {$regex:?0,$options:'i'} } ], $and: [ { 'isDelete' : ?1 }]}")

	
	Page<HsnEntity> Search(String keyword, Boolean isDelete, String string, Pageable pagingSort);
	/*
	 * @Query("{'descriptiion': ?0}") List<HsnEntity> findByDescriptiion(String
	 * descriptiion);
	 */
    
	/*
	 * @Query("{'hsnCode': ?0}") List<HsnEntity> findByhsnCode(Integer hsn_code);
	 * 
	 * @Query("{'hsnCode': ?0}") HsnEntity findByhsnCode1(Integer hsnCode);
	 */
	
	

}
