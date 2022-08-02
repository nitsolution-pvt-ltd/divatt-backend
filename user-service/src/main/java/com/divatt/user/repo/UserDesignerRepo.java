package com.divatt.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.UserDesignerEntity;


public interface UserDesignerRepo extends MongoRepository<UserDesignerEntity,Integer>{
	Optional<UserDesignerEntity> findByUserId(Long userId);
	List<UserDesignerEntity> findByDesignerIdAndIsFollowing(Long designerId,Boolean isFollowing);
	Long countByDesignerId(Long designerId);
	

}
