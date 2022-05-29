package com.divatt.designer.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.designer.entity.profile.DesignerPersonalInfoEntity;

@Repository
public interface DesignerPersonalInfoRepo extends MongoRepository<DesignerPersonalInfoEntity, Long>{
	Optional<DesignerPersonalInfoEntity> findByDesignerId(Long designerId);
}
