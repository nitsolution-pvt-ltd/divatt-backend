package com.divatt.user.repo.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.cart.UserCartEntity;


public interface UserCartRepo extends MongoRepository<UserCartEntity, Integer> {

	Optional<UserCartEntity> findByProductIdAndUserId(Integer ProductId, Integer UserId);

	List<UserCartEntity> findByUserId(Integer UserId);

	void deleteByProductIdAndUserId(Integer productId, Integer userId);

	List<UserCartEntity> findByUserIdAndProductId(Integer userId, Integer productId);

	void deleteByUserId(Integer userId);

}
