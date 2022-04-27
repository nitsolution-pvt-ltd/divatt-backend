package com.divatt.admin.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.admin.entity.specification.SpecificationEntity;

@Repository
public interface SpecificationRepo extends MongoRepository<SpecificationEntity, Integer>{

	Optional<SpecificationEntity> findByName(String name);

}
