package com.divatt.admin.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.admin.entity.ColourMetaEntity;

public interface AdminMDataRepo extends MongoRepository<ColourMetaEntity, Integer>{

}
