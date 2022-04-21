package com.divatt.designer.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.designer.entity.profile.DesignerLoginEntity;





public interface DesignerLoginRepo extends MongoRepository<DesignerLoginEntity, Object>{
	
	Optional<DesignerLoginEntity> findByEmail(String email);
	Page<DesignerLoginEntity> findByIsApproved(Boolean isApproved  ,Pageable pagingSort);
	Page<DesignerLoginEntity> findByIsProfileSubmitted(Boolean isProfileSubmitted  ,Pageable pagingSort);
	Page<DesignerLoginEntity> findByIsProfileCompleated(Boolean isProfileCompleated  ,Pageable pagingSort);
	public Page<DesignerLoginEntity> findByIsDeleted(Boolean isDeleted,Pageable pagingSort);
	@Query(value = "{ $or: [ { 'email' : {$regex:?0,$options:'i'} } ],$and: [ { 'isDeleted' : ?1 }]}")
	public Page<DesignerLoginEntity> SearchByDelete(String sortKey, Boolean isDeleted,Pageable pageable);
	@Query(value = "{ $or: [ { 'email' : {$regex:?0,$options:'i'} } ],$and: [ { 'isApproved' : ?1 }]}")
	public Page<DesignerLoginEntity> SearchByApproved(String sortKey, Boolean isApproved,Pageable pageable);
	@Query(value = "{ $or: [ { 'email' : {$regex:?0,$options:'i'} } ],$and: [ { 'isProfileCompleated' : ?1 }]}")
	public Page<DesignerLoginEntity> SearchByProfileCompleated(String sortKey, Boolean isProfileCompleated,Pageable pageable);
	@Query(value = "{ $or: [ { 'email' : {$regex:?0,$options:'i'} } ],$and: [ { 'isProfileSubmitted' : ?1 }]}")
	public Page<DesignerLoginEntity> SearchByProfileSubmitted(String sortKey, Boolean isProfileSubmitted,Pageable pageable);
}
