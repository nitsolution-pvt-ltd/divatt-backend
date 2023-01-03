package com.divatt.designer.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.designer.entity.product.ProductCustomizationEntity;


@Repository
public interface ProductCustomizationRepo extends MongoRepository<ProductCustomizationEntity, Integer>{

	ProductCustomizationEntity findByproductName(String productName);

	Page<ProductCustomizationEntity> findByCategoryName(String categoryName, Pageable pagingSort);

	
	@Query(value = "{ $or: [ { 'designerId' : {$regex:?0,$options:'i'} }, { 'productName' : {$regex:?0,$options:'i'} },{ 'categoryName' : {$regex:?0,$options:'i'} },{ 'subCategoryName' : {$regex:?0,$options:'i'} } ] }")
	Page<ProductCustomizationEntity> Search(String keyword, Pageable pagingSort);

}
