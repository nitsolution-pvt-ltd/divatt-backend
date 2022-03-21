package com.divatt.auth.repo;



import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.divatt.auth.entity.LoginEntity;
import com.divatt.auth.entity.PasswordResetEntity;

public interface PasswordResetRepo extends MongoRepository<PasswordResetEntity, Long>{
	
	public Optional<PasswordResetEntity> findByPrtoken(String prtoken);

}
