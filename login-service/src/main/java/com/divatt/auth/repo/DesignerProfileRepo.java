package com.divatt.auth.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.divatt.auth.entity.DesignerProfileEntity;

public interface DesignerProfileRepo extends MongoRepository<DesignerProfileEntity, Long>{
	DesignerProfileEntity findByDesignerId(Long designerId);
}
