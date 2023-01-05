package com.divatt.auth.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginAdminData implements UserDetails{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



		public LoginAdminData() {
		super();
	}

		private String token;
		private Object UId;
		private String email;
		private String password;
		private String message;
		private List<GrantedAuthority> role;
		private String authority;
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


		public String getAuthority() {
			return authority;
		}


		public void setAuthority(String authority) {
			this.authority = authority;
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
//			this.authority = this.role.get(0).getAuthority();
			this.authority = "ADMIN";
		}


		public LoginAdminData(String token, Object uId, String email, String password, String message,
				List<GrantedAuthority> role, String authority, int status) {
			super();
			this.token = token;
			UId = uId;
			this.email = email;
			this.password = password;
			this.message = message;
			this.role = role;
			this.authority = authority;
			this.status = status;
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
			return password;
		}

		@Override
		public String getUsername() {
			return email;
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
}
