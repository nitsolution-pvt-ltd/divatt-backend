package com.divatt.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.UserDesignerEntity;


public interface UserDesignerRepo extends MongoRepository<UserDesignerEntity,Integer>{
	Optional<UserDesignerEntity> findByUserId(Long userId);

}
