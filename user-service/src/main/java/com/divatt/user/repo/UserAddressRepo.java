package com.divatt.user.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.user.entity.UserAddressEntity;

public interface UserAddressRepo extends MongoRepository<UserAddressEntity, Long>{
	
	List<UserAddressEntity> findByUserId(Long userId);

}
