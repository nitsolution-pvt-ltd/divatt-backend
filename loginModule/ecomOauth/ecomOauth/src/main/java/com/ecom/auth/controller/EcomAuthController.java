package com.ecom.auth.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aryansh
 *
 */
@RestController
@SuppressWarnings("unchecked")
@RequestMapping("/auth")
public class EcomAuthController {

	@GetMapping("/facebook")
	public Map<String, Object> facebookAuth(Principal principal) {

		OAuth2Authentication authentication = (OAuth2Authentication) principal;
		Map<String, Object> details = (Map<String, Object>) authentication.getUserAuthentication().getDetails();
		return details;
	}
	
	@GetMapping("/google")
	public Map<String, Object>  googleAuth(Principal principal) {

		OAuth2Authentication authentication = (OAuth2Authentication) principal;
		Map<String, Object> details = (Map<String, Object>) authentication.getUserAuthentication().getDetails();
		return details;
	}
}
