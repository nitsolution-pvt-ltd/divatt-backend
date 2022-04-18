package com.divatt.user.repo.cart;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.cart.UserCartEntity;


public interface UserCartRepo  extends MongoRepository<UserCartEntity, Integer>{
	
	Optional<UserCartEntity> findByProductIdAndUserId(Integer ProductId,Integer UserId);

}
