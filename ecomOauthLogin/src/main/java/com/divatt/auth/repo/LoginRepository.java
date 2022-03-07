package com.divatt.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.divatt.auth.entity.LoginEntity;





public interface LoginRepository extends JpaRepository<LoginEntity, Long> {

	Optional<LoginEntity> findByUserName(String userName);

	public Optional<LoginEntity> findByEmail(String email);
	
	@Query(value = "select login from LoginEntity login where userName = ?1 and accessToken = ?2")
	Optional<LoginEntity> findByUserNameAndToken(String userName,String accessToken);
	

}
