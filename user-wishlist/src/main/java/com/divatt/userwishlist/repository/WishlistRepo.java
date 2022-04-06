package com.divatt.userwishlist.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.divatt.userwishlist.entity.WishlistEntity;


@Repository
public interface WishlistRepo extends MongoRepository<WishlistEntity,Integer>{
	
	Optional<WishlistEntity> findByProductId(Integer ProductId);

}
