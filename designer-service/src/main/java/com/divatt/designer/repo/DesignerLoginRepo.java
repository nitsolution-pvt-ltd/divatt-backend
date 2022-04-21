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
	List<DesignerLoginEntity> findByIsApproved(Boolean isApproved);
	List<DesignerLoginEntity> findByIsProfileSubmitted(Boolean isProfileSubmitted);
	public Page<DesignerLoginEntity> findByIsDeleted(Boolean isDeleted,Pageable pagingSort);
	@Query(value = "{ $or: [ { 'email' : {$regex:?0,$options:'i'} } ],$and: [ { 'isDeleted' : ?1 }]}")
	public Page<DesignerLoginEntity> Search(String sortKey, Boolean isDeleted,Pageable pageable);
}
