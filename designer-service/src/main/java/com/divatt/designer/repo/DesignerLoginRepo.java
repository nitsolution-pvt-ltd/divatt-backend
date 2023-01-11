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
	
	List<DesignerLoginEntity> findByIsDeletedAndAndIsProfileCompletedAndAccountStatusAndDesignerCurrentStatus(Boolean isDeleted, Boolean isprofileStatus,
			String AccountStatus, String designerCurrentStatus);
	
	@Query("{'isDeleted':?0,'isProfileCompleted':?1, 'accountStatus':?2}")
	List<DesignerLoginEntity> findByIsDeletedAndisProfileCompletedAndAccountStatus(Boolean isDeleted, Boolean isProfileCompleted,
			String AccountStatus);
	List<DesignerLoginEntity> findByIsDeletedAndProfileStatusAndDId(Boolean isDeleted, String profileStatus, long dId);

	List<DesignerLoginEntity> findByProfileStatusAndAccountStatusAndIsDeleted(String profileStatus, String acountStatus,
			Boolean isDeleted);

	List<DesignerLoginEntity> findByProfileStatusAndAccountStatusAndIsProfileCompleted(String profileStatus,
			String acountStatus, Boolean isProfileCompleted);

	@Query("{'isDeleted':?0,'profileStatus':?1, 'accountStatus':?2}")
	public Page<DesignerLoginEntity> findByIsDeletedAndProfileStatusAndAccountStatus(Boolean isDeleted,
			String profileStatus, String accountStatus, Pageable pagingSort);

	@Query("{'isDeleted':?0,'profileStatus':?1}")
	public Page<DesignerLoginEntity> findByIsDeletedAndProfileStatus(Boolean isDeleted, String profileStatus,
			Pageable pagingSort);

//	@Query("{'isDeleted':?0,'profileStatus':?1}")
//	@Query("{ $or: [ { 'profileStatus' : {$regex:?1,$options:'i'} }, {'profileStatus' : {$regex:'SAVED',$options:'i'} } ], $and: [{'isDeleted':?0}] }")
//	public Page<DesignerLoginEntity> findByIsDeletedAndProfileStatusAndAcountStatusForCompleted(Boolean isDeleted, String profileStatus, Pageable pagingSort);

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

	@Query("{'isDeleted':?0, 'isProfileCompleted':?1, 'profile_status':?2}")
	List<DesignerLoginEntity> findByDeletedAndIsProfileCompletedAndProfileStatus(boolean isDeleted,
			Boolean isProfileCompleted, String profileStatus);

	@Query("{'isDeleted':?0,'isProfileCompleted':?1, 'profileStatus':?2}")
	public Page<DesignerLoginEntity> findByIsDeletedAndIsProfileCompletedAndProfileStatus(Boolean isDeleted,
			Boolean isProfileCompleted, String profileStatus, Pageable pagingSort);

	@Query("{'isDeleted':?0,'isProfileCompleted':?1,'profile_status':?2,'account_status':?3}")
	Page<DesignerLoginEntity> findByIsDeletedAndIsProfileCompletedAndProfileStatusAndAccountstatus(Boolean isDeleted,
			Boolean isProfileCompleted, String profileStatus, String accountStatus, Pageable pagingSort);

	Page<DesignerLoginEntity> findBydIdInAndDesignerCurrentStatus(List<Long> dId, String Status, Pageable pagingSort);
	List<DesignerLoginEntity> findBydIdInAndDesignerCurrentStatus(List<Long> dId,String Status);

	@Query("{'isDeleted':?0,'isProfileCompleted':?1, 'profileStatus':?2}")
	List<DesignerLoginEntity> findByIsDeletedAndIsProfileCompletedAndProfileStatus(Boolean isDeleted,
			Boolean isProfileCompleted, String profileStatus);

	@Query("{'isDeleted':?0,'isProfileCompleted':?1, 'profileStatus':?2, 'accountStatus':?3}")
	List<DesignerLoginEntity> findByIsDeletedAndIsProfileCompletedAndProfileStatusAndAccountStatus(Boolean isDeleted,
			Boolean isProfileCompleted, String profileStatus, String accountStatus);
	
//	@Query("{'isDeleted':?0,'isProfileCompleted':?1, 'profileStatus':?2, 'profileStatus':?3}")
	Page<DesignerLoginEntity> findByIsDeletedAndIsProfileCompletedAndProfileStatusOrProfileStatus(Boolean isDeleted,
			Boolean isProfileCompleted, String profileStatus, String accountStatus, Pageable pagingSort);

	Page<DesignerLoginEntity> findBydIdIn(List<Long> collect, Pageable pagingSort);
}
