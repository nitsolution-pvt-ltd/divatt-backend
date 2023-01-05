package com.divatt.auth.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.auth.entity.UserLoginEntity;
@Repository
public interface UserLoginRepo extends MongoRepository<UserLoginEntity, Long>{
	Optional<UserLoginEntity> findByEmail(String email);
}
