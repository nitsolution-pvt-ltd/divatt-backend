package com.divatt.designer.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.designer.entity.profile.DesignerProfileEntity;

public interface DesignerProfileRepo extends MongoRepository<DesignerProfileEntity, Long> {

	Optional<DesignerProfileEntity> findBydesignerId(Long valueOf);

	@Query("{'boutique_profile.boutique_name':?0}")
	Optional<DesignerProfileEntity> findByBoutiqueName(String boutiqueProfile);
	
	@Query("{'designer_profile.designer_category':?0}")
	List<DesignerProfileEntity> findByDesignerCategory(String designerCategory);
	List<DesignerProfileEntity> findByDesignerIdIn(List<Long> designerId);
	

}
