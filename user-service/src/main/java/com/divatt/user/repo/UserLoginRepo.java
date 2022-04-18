package com.divatt.user.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.UserLoginEntity;



public interface UserLoginRepo extends MongoRepository<UserLoginEntity, Long>{
	Optional<UserLoginEntity> findByEmail(String email);
}
