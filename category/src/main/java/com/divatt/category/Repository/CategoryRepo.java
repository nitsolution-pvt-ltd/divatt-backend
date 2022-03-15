package com.divatt.category.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.category.Entity.CategoryEntity;

public interface CategoryRepo extends MongoRepository<CategoryEntity, Long>{

}
