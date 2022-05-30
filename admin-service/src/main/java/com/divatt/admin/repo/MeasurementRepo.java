package com.divatt.admin.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.ProductMeasurementEntity;
@Repository
public interface MeasurementRepo extends MongoRepository<ProductMeasurementEntity, Integer>{

	boolean findBySubCategoryName(String subCategoryName);

	Page<ProductMeasurementEntity> findByIsDeleteAndMetaKey(Boolean isDelete, String metakey, Pageable pagingSort);

	
	@Query(value = "{ $or: [ { 'categoryName' : {$regex:?0,$options:'i'} }, { 'subCategoryName' : {$regex:?0,$options:'i'} },{ 'isActive' : {$regex:?0,$options:'i'} } ], [ { 'metakey' : ?1,'isDelete' : ?2 }]}")
	Page<ProductMeasurementEntity> Search(String keyword, String metakey, Boolean isDelete, Pageable pagingSort);

}
