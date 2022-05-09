package com.divatt.designer.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.designer.entity.profile.DesignerLoginEntity;





public interface DesignerLoginRepo extends MongoRepository<DesignerLoginEntity, Long>{
	
	Optional<DesignerLoginEntity> findByEmail(String email);
	
	List<DesignerLoginEntity> findByIsDeletedAndIsApproved(Boolean isDeleted,String IsApproved);
	
	List<DesignerLoginEntity> findByProfileStatus(String profileStatus);
	
	public Page<DesignerLoginEntity> findByIsDeletedAndProfileStatus(Boolean isDeleted,String profileStatus,Pageable pagingSort);
	
	public Page<DesignerLoginEntity> findByIsDeleted(Boolean isDeleted,Pageable pagingSort);
	@Query(value = "{ $or: [ { 'email' : {$regex:?0,$options:'i'} } ],$and: [ { 'isDeleted' : ?1 }]}")
	public Page<DesignerLoginEntity> SearchByDelete(String sortKey, Boolean isDeleted,Pageable pageable);
	
	@Query(value = "{ $or: [ { 'email' : {$regex:?0,$options:'i'} } ],$and: [ { 'isDeleted' : ?1 }],$and: [ { 'profileStatus' : ?2 }]}")
	public Page<DesignerLoginEntity> SearchByDeletedAndProfileStatus(String sortKey, Boolean isDeleted,String profileStatus,Pageable pageable);


	
}
