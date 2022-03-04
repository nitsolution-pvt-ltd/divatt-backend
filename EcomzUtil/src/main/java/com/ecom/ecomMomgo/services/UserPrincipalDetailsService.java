package com.ecom.ecomMomgo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecom.ecomMongo.repository.UserRepository;
import com.ecom.ecomMongo.model.User;
import com.ecom.ecomMongo.model.UserPrincipal;


/**
 * @author aryansh
 *
 */
@Service
public class UserPrincipalDetailsService implements UserDetailsService 
{
	 @Autowired
	 private UserRepository userRepository;
	 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		 User user = this.userRepository.findByUserName(username);
		 UserPrincipal userPrincipal = null;
		 if (user != null) 
		 {
	        userPrincipal = new UserPrincipal(user);
		 }else 
		 {
			 throw new UsernameNotFoundException("User not exist with name : " + username);
		 }

	 return userPrincipal;
	}
}
