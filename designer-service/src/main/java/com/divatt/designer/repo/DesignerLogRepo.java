package com.divatt.designer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.designer.entity.profile.DesignerLogEntity;

@Repository
public interface DesignerLogRepo extends MongoRepository<DesignerLogEntity, Long>{

}
