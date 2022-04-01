package com.divatt.auth.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.divatt.auth.entity.AdminLoginEntity;
import com.divatt.auth.entity.DesignerLoginEntity;
import com.divatt.auth.entity.LoginAdminData;
import com.divatt.auth.entity.LoginDesignerData;
import com.divatt.auth.exception.CustomException;
import com.divatt.auth.repo.AdminLoginRepository;
import com.divatt.auth.repo.DesignerLoginRepo;


@Service
public class LoginUserDetails implements UserDetailsService {
	
	@Autowired
	private AdminLoginRepository adminLoginRepository;
	
	@Autowired
	private DesignerLoginRepo designerLoginRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<AdminLoginEntity> admin = adminLoginRepository.findByEmail(username);
		if (admin.isPresent()) {
			admin.orElseThrow(() -> new CustomException("Not found "+username));
			return admin.map(LoginAdminData :: new).get();
		} else {
			Optional<DesignerLoginEntity> designer = designerLoginRepo.findByEmail(username);
			if (designer.isPresent()) {
				designer.orElseThrow(() -> new CustomException("Not found "+username));
				return designer.map(LoginDesignerData :: new).get();
			}
			
				throw new CustomException("Username not found");
		}
	}
	


}
