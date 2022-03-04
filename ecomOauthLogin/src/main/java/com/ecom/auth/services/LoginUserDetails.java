package com.ecom.auth.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecom.auth.entity.LoginEntity;
import com.ecom.auth.entity.LoginUserData;
import com.ecom.auth.exception.CustomException;
import com.ecom.auth.repo.LoginRepository;


@Service
public class LoginUserDetails implements UserDetailsService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<LoginEntity> findByUserName = loginRepository.findByUserName(username);
		if (findByUserName.isPresent()) {
			findByUserName.orElseThrow(() -> new CustomException("Not found "+username));
			return findByUserName.map(LoginUserData :: new).get();
		} else {
			
				throw new CustomException("Username not found");
		}
	}
	


}
