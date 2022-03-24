package com.divatt.databaseservice.category.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.databaseservice.category.model.CategoryEntity;



@Repository
public interface CategoryRepo extends MongoRepository<CategoryEntity, Integer>{
	
	Optional<CategoryEntity> findByCategoryName(String categoryName);
	
	Page<CategoryEntity> findByIsDeleted(Boolean isDeleted,Pageable pagingSort);

}
