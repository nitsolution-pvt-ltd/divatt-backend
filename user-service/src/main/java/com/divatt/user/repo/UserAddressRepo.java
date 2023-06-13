package com.divatt.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.divatt.user.entity.UserAddressEntity;

public interface UserAddressRepo extends MongoRepository<UserAddressEntity, Long>{
	
	List<UserAddressEntity> findByUserId(Long userId);
	@Query("{'email':?0}")
	Optional<UserAddressEntity> findByUserEmail(String email);
	

}
