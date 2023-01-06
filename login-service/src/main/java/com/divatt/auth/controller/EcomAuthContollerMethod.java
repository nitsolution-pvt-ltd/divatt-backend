package com.divatt.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.divatt.auth.entity.AdminLoginEntity;
import com.divatt.auth.entity.GlobalEntity;
import com.divatt.auth.entity.GlobalResponse;
import com.divatt.auth.entity.SendMail;

public interface EcomAuthContollerMethod {
	
	
	public ResponseEntity<?> superAdminLogin(@RequestBody AdminLoginEntity loginEntity);
	public ResponseEntity<String> sendMail(@RequestBody() SendMail senderMailId) ;
	public GlobalResponse mailForgotPasswordLink(@PathVariable("email") String email) ;
	public GlobalResponse resetPassword(@PathVariable("link") String link, @PathVariable("linkTime") String linkTime, @RequestBody GlobalEntity globalEntity);
	public GlobalResponse changePassword(@RequestBody GlobalEntity globalEntity,@RequestHeader(name = "Authorization") String token);
	
	public static boolean check() throws ClassNotFoundException {
		Class<?> forName = Class.forName("com.divatt.auth.controller.EcomAuthController");
		int MCount = forName.getDeclaredMethods().length;
		Class<?>[] interfaces = forName.getInterfaces();
		  Class<?> theFirstAndOnlyInterface = interfaces[0]; 
		
		 int length = theFirstAndOnlyInterface.getMethods().length;
		 
		 if(length == MCount)
			  return true;
		return false;
	}
	
	

}
