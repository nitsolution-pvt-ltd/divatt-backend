package com.divatt.admin.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.ColourMetaEntity;

@Repository
public interface AdminMDataRepo extends MongoRepository<ColourMetaEntity, Integer>{

	Page<ColourMetaEntity> findByMetaKey(String metaKey,String string, Pageable pagingSort);

	/*
	 * @Query(value =
	 * "{ $or: [ { 'colorName' : {$regex:?0} }, { 'colorValue' : {$regex:?0} }]}")
	 * Page<ColourMetaEntity> Search(String keyword, String string, Pageable
	 * pagingSort);
	 */
	
	
@Query(value = "{ $or: [ { 'colorName' : {$regex:?0,$options:'i'} }, { 'colorValue' : {$regex:?0,$options:'i'} }]}") 
Page<ColourMetaEntity> Search(String keyword, String string, Pageable pagingSort);
	 

}
