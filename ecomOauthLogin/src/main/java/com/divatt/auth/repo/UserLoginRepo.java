package com.divatt.auth.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.auth.entity.DesignerLoginEntity;
import com.divatt.auth.entity.UserLoginEntity;

public interface UserLoginRepo extends MongoRepository<UserLoginEntity, Long>{
	Optional<UserLoginEntity> findByEmail(String email);
}
