package com.divatt.auth.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.auth.entity.AdminLoginEntity;




@Repository
public interface AdminLoginRepository extends MongoRepository<AdminLoginEntity, Object> {

//	Optional<LoginEntity> findByUserName(String userName);
	
	public Optional<AdminLoginEntity> findByEmail(String email);
	
	
	
//	@Query(value = "select login from LoginEntity login where userName = ?1 and accessToken = ?2")
//	Optional<LoginEntity> findByUserNameAndToken(String userName,String accessToken);
	

}
