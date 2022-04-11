package com.divatt.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.UserDesignerEntity;
import com.divatt.user.entity.WishlistEntity;

public interface UserDesignerRepo extends MongoRepository<UserDesignerEntity,Integer>{

}
