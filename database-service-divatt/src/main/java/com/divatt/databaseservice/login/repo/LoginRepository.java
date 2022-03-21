package com.divatt.databaseservice.login.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.databaseservice.login.model.LoginEntity;

public interface LoginRepository extends MongoRepository<LoginEntity, Long> {
	public Optional<LoginEntity> findByEmail(String email);
}
