package com.divatt.auth.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.divatt.auth.entity.GlobalEntity;
import com.divatt.auth.entity.GlobalResponse;
import com.divatt.auth.entity.LoginEntity;
import com.divatt.auth.exception.CustomException;
import com.divatt.auth.helper.JwtUtil;
import com.divatt.auth.repo.LoginRepository;



@Service
public class LoginService {

	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public GlobalResponse changePass(GlobalEntity globalEntity, String token) {
		try {
			
			if (!token.equals(null)) {
				Optional<LoginEntity> findByUserName = loginRepository.findByUserName(globalEntity.getUserName());
				if (findByUserName.isPresent()) {
					if (globalEntity.getUserName().equals(jwtUtil.extractUsername(token.substring(7)))) {
						if(passwordEncoder.matches(globalEntity.getOldPass(), findByUserName.get().getPassword())) {
							LoginEntity login = new LoginEntity();
							login.setFirstName(findByUserName.get().getFirstName());
							login.setLastName(findByUserName.get().getLastName());
							login.setAccessToken(findByUserName.get().getAccessToken());
							login.setEmail(findByUserName.get().getEmail());
							login.setPpic(findByUserName.get().getPpic());
							login.setPassword(passwordEncoder.encode(globalEntity.getNewPass()));
							login.setUid(findByUserName.get().getUid());
							login.setUserName(findByUserName.get().getUserName());
							LoginEntity save = loginRepository.save(login);
							if(save.equals(null)) {
								throw new CustomException("Data Not Save Try Again");
							}else {
								return new GlobalResponse("SUCCESS", "Password changed", 200);
							}
							
						}else {
							throw new CustomException("Password not match");
						}
						
					} else {
						throw new CustomException("UserName Not Matched");
					}
				} else {
					throw new CustomException("User not found");
				}
			} else {
				throw new CustomException("Token not valid");
			}
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
		
	}
}
