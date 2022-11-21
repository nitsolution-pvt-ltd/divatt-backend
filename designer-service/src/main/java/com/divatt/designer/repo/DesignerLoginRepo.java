package com.divatt.designer.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.designer.entity.profile.DesignerLoginEntity;

public interface DesignerLoginRepo extends MongoRepository<DesignerLoginEntity, Long> {

	Optional<DesignerLoginEntity> findByEmail(String email);

	List<DesignerLoginEntity> findByIsDeletedAndProfileStatusAndAccountStatus(Boolean isDeleted, String profileStatus,
			String AccountStatus);

	List<DesignerLoginEntity> findByIsDeletedAndProfileStatusAndDId(Boolean isDeleted, String profileStatus, long dId);

	List<DesignerLoginEntity> findByProfileStatus(String profileStatus);

	@Query("{'isDeleted':?0,'profileStatus':?1}")
	public Page<DesignerLoginEntity> findByIsDeletedAndProfileStatus(Boolean isDeleted, String profileStatus,
			Pageable pagingSort);

	public Page<DesignerLoginEntity> findByIsDeleted(Boolean isDeleted, Pageable pagingSort);

	@Query(value = "{ $or: [ { 'email' : {$regex:?0,$options:'i'} } ],$and: [ { 'isDeleted' : ?1 }]}")
	public Page<DesignerLoginEntity> SearchByDelete(String sortKey, Boolean isDeleted, Pageable pageable);

	
	@Query(value = "{ $or: [ { 'email' : {$regex:?0,$options:'i'} } ],$and: [ { 'isDeleted' : ?1 }],$and: [ { 'profileStatus' : ?2 }]}")
	Page<DesignerLoginEntity> SearchByDeletedAndProfileStatus(String sortKey, Boolean isDeleted, String profileStatus,
			Pageable pageable);

	@Query("{profileStatus:?0}")
	Page<DesignerLoginEntity> findDesignerProfileStatus(String profileStatus, Pageable pagingSort);

	@Query("{'isDeleted':?0}")
	Page<DesignerLoginEntity> findDesignerisDeleted(boolean isDeleted, Pageable pagingSort);

	@Query("{'isDeleted':?0}")
	List<DesignerLoginEntity> findByDeleted(boolean isDeleted);
	
	@Query("{'isDeleted':?0, 'isProfileCompleted':?1}")
	List<DesignerLoginEntity> findByDeletedAndIsProfileCompleted(boolean isDeleted, Boolean isProfileCompleted);
	
	@Query("{'isDeleted':?0,'isProfileCompleted':?1}")
	public Page<DesignerLoginEntity> findByIsDeletedAndIsProfileCompleted(Boolean isDeleted, Boolean isProfileCompleted, Pageable pagingSort);

}
