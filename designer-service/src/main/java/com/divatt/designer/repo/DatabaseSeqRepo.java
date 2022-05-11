package com.divatt.designer.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.designer.entity.DatabaseSequence;
import com.divatt.designer.entity.profile.DesignerLoginEntity;

public interface DatabaseSeqRepo extends MongoRepository<DatabaseSequence, String>{

}
