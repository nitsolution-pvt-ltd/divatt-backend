package com.divatt.auth.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginAdminData implements UserDetails{
	
		public LoginAdminData() {
		super();
		// TODO Auto-generated constructor stub
	}

		private String token;
		private Object UId;
		private String email;
		private String password;
		private String message;
		private List<GrantedAuthority> role;
		private int status;


		
		public LoginAdminData(AdminLoginEntity vendor) {
			this.email = vendor.getEmail();
			this.password = vendor.getPassword();
			this.role = Stream.of(vendor.getRole()).map(SimpleGrantedAuthority::new).collect(Collectors.toList())  ;
			
		}
		
		
		public String getToken() {
			return token;
		}


		public void setToken(String token) {
			this.token = token;
		}


		


		public void setUsername(String username) {
			this.email = username;
		}


		public void setPassword(String password) {
			this.password = password;
		}
		
		public String getMessage() {
			return message;
		}


		public void setMessage(String message) {
			this.message = message;
		}


		public int getStatus() {
			return status;
		}


		public void setStatus(int status) {
			this.status = status;
		}


		public LoginAdminData(String token, Object uId, String username, String password, String message, int status,String role) {
			super();
			this.token = token;
			this.UId = uId;
			this.email = username;
			this.password = password;
			this.message = message;
			this.status = status;
			this.role = Stream.of(role).map(SimpleGrantedAuthority::new).collect(Collectors.toList())  ;
		}


		public Object getUId() {
			return UId;
		}


		public void setUId(Long uId) {
			UId = uId;
		}


		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			
			return role;
		}

		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return password;
		}

		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return email;
		}

		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return true;
		}

}
