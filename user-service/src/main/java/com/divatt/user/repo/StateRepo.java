package com.divatt.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.StateEntity;

public interface StateRepo extends MongoRepository<StateEntity, Long> {

}
