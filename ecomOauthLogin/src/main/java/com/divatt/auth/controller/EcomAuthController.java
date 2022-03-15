package com.divatt.auth.controller;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.auth.entity.LoginEntity;
import com.divatt.auth.entity.LoginUserData;
import com.divatt.auth.exception.CustomException;
import com.divatt.auth.helper.JwtUtil;
import com.divatt.auth.repo.LoginRepository;

import com.divatt.auth.services.LoginUserDetails;


@RestController
@SuppressWarnings("All")
@RequestMapping("/auth")

public class EcomAuthController {
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	
	@PostMapping("/login")
	public ResponseEntity<?> superAdminLogin(@RequestBody LoginEntity loginEntity) {
		
	Logger LOGGER = LoggerFactory.getLogger(EcomAuthController.class);
		
		LOGGER.info("Inside - LoginContoller.superAdminLogin()");
		
		try {
			try {
				this.authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginEntity.getEmail(), loginEntity.getPassword()));
			} catch (Exception e) {
				throw new CustomException(e.getMessage());
			}

			UserDetails vendor = this.loginUserDetails.loadUserByUsername(loginEntity.getEmail());

			String token = jwtUtil.generateToken(vendor);

			Optional<LoginEntity> findByUserName = loginRepository.findByEmail(vendor.getUsername());
			
			LoginEntity loginEntityAfterCheck = findByUserName.get();
			loginEntityAfterCheck.setAuth_token(token);
			LoginEntity save = loginRepository.save(loginEntityAfterCheck);
			
			if(save.equals(null)) {
				throw new CustomException("Data Not Save Try Again");
			}

			return ResponseEntity.ok(new LoginUserData(token,findByUserName.get().getId(),findByUserName.get().getEmail() , findByUserName.get().getPassword(), "Login successful", 200));
		
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}	
	
	@GetMapping("/admin/testapi")
	public String test() {
		return "Jwtwork";
	}
	
	
}
