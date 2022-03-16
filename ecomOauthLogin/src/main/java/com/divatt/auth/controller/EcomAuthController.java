package com.divatt.auth.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.auth.entity.GlobalResponse;
import com.divatt.auth.entity.LoginEntity;
import com.divatt.auth.entity.LoginUserData;
import com.divatt.auth.entity.SendMail;
import com.divatt.auth.exception.CustomException;
import com.divatt.auth.helper.JwtUtil;
import com.divatt.auth.repo.LoginRepository;

import com.divatt.auth.services.LoginUserDetails;
import com.divatt.auth.services.MailService;


@RestController
@SuppressWarnings("All")
@RequestMapping("/auth")

public class EcomAuthController {
	
	@Autowired
	private MailService mailService;
	
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

	Logger LOGGER = LoggerFactory.getLogger(EcomAuthController.class);
	
	@PostMapping("/login")
	public ResponseEntity<?> superAdminLogin(@RequestBody LoginEntity loginEntity) {
		
	
		
		LOGGER.info("Inside - EcomAuthController.superAdminLogin()");
		
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
	
	
	@PostMapping("/sendMail")
	public ResponseEntity<String> sendMail(@RequestBody() SendMail senderMailId) {
		LOGGER.info("Inside - LoginContoller.sendMail()");
		
		try {
			mailService.sendEmail(senderMailId.getSenderMailId(), senderMailId.getSubject(),senderMailId.getBody(),senderMailId.isEnableHtml());
//			return new GlobalResponse("SUCCESS","Mail Send Successfully", 200);
			return new ResponseEntity<>("Mail Send Successfully" ,HttpStatus.OK);
			
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}


	
	
	
	
	
	
	
	@GetMapping("/admin/testapi")
	public String test() {
		return "Jwtwork";
	}
	
	
}
