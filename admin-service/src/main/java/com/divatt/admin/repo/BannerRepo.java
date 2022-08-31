package com.divatt.admin.repo;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.BannerEntity;


@Repository
public interface BannerRepo extends MongoRepository<BannerEntity, Long> {
	
	List<BannerEntity> findByTitle(String title);	
	
	Page<BannerEntity> findByIsDeleted(Boolean isDeleted,Pageable pagingSort);
	
	@Query(value = "{ $or: [ { 'title' : {$regex:?0,$options:'i'} }, { 'description' : {$regex:?0,$options:'i'} },{ 'image' : {$regex:?0,$options:'i'} },{ 'start_date' : {$regex:?0,$options:'i'} },{ 'end_date' : {$regex:?0,$options:'i'} },{ 'createdOn' : {$regex:?0,$options:'i'} } ], $and: [ { 'is_deleted' : ?1 }]}")
    Page<BannerEntity> Search(String sortKey, Boolean isDeleted,Pageable pageable);

}