package com.ecom.ecomMongo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.ecomMongo.model.User;

/**
 * @author aryansh
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> 
{
    
	User findByUserName(String username);

}
