package com.divatt.auth.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.auth.entity.DesignerProfile;

public interface DesignerProfileRepo extends MongoRepository<DesignerProfile, Long>{
	DesignerProfile findByDesignerId(Long designerId);
}
