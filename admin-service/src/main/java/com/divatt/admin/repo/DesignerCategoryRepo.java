package com.divatt.admin.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.admin.entity.DesignerCategoryEntity;

public interface DesignerCategoryRepo extends MongoRepository<DesignerCategoryEntity, Integer>{

}
