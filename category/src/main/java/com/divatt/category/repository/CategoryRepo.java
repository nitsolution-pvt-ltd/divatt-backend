package com.divatt.category.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.category.entity.CategoryEntity;

@Repository
public interface CategoryRepo extends MongoRepository<CategoryEntity, Integer>{

	Optional<CategoryEntity> findByCategoryName(String categoryName);
	
//	@Query(value = "SELECT instObj FROM SubCategoryEntity instObj")
	Page<CategoryEntity> findByIsDeleted(Boolean isDeleted,Pageable pagingSort);
}
