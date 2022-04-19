package com.divatt.designer.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.designer.entity.profile.DesignerLoginEntity;





public interface DesignerLoginRepo extends MongoRepository<DesignerLoginEntity, Object>{
	
	Optional<DesignerLoginEntity> findByEmail(String email);
	List<DesignerLoginEntity> findByIsApproved(Boolean isApproved);
	List<DesignerLoginEntity> findByIsProfileSubmitted(Boolean isProfileSubmitted);
}
