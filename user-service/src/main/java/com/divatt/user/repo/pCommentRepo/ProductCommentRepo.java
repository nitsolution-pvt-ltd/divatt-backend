package com.divatt.user.repo.pCommentRepo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.PCommentEntity.ProductCommentEntity;

@SuppressWarnings("rawtypes")
public interface ProductCommentRepo extends MongoRepository<ProductCommentEntity, Integer>{
	
	Optional<ProductCommentEntity> findByProductIdAndUserId(Integer ProductId,Integer UserId);

}
