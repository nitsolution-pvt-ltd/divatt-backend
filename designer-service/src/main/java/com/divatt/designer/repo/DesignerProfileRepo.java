package com.divatt.designer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.designer.entity.profile.DesignerProfileEntity;

public interface DesignerProfileRepo extends MongoRepository<DesignerProfileEntity, Long>{

	DesignerProfileEntity findBydesignerId(Long valueOf);

}
