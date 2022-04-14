package com.divatt.user.repo.cart;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.cart.CartEntity;

public interface CartRepo extends MongoRepository<CartEntity, Integer>{

}
