package com.divatt.designer.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.designer.entity.profile.DesignerProfileEntity;

public interface DesignerProfileRepo extends MongoRepository<DesignerProfileEntity, Long>{

	Optional<DesignerProfileEntity> findBydesignerId(Long valueOf);

}
