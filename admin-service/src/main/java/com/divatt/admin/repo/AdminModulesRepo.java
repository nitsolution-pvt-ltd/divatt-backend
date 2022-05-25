package com.divatt.admin.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.admin.entity.AdminModules;
import com.divatt.admin.entity.LoginEntity;


public interface AdminModulesRepo extends MongoRepository<AdminModules, Long>{
	
	public Optional<AdminModules> findByRoleName(Long roleName);
	public Optional<AdminModules> findByMetaKey(String metaKey);
	
	public List<AdminModules> findByIsDeleted(Boolean isDeleted);
	
	public Page<AdminModules> findByIsDeleted(Boolean isDeleted,Pageable pagingSort);
	
	@Query(value = "{ $or: [ { 'roleName' : {$regex:?0,$options:'i'} } ],$and: [ { 'isDeleted' : ?1 }]}")
	public Page<AdminModules> Search(String sortKey, Boolean isDeleted,Pageable pageable);
}
