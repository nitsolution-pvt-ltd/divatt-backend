package com.divatt.auth.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.divatt.auth.entity.DesignerLoginEntity;
@Repository
public interface DesignerLoginRepo extends MongoRepository<DesignerLoginEntity, Object>{
	
	Optional<DesignerLoginEntity> findByEmail(String email);
	
//	@Query(value = "{ $and: [ { 'email' : ?0 } ]},{$ne: [ { 'profile_status' : ?1 }]}")
	Optional<DesignerLoginEntity> findByEmailAndAccountStatusNot(String email, String profileStatus);
}
 