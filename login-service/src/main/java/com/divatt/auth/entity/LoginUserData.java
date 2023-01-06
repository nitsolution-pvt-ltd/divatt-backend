package com.divatt.auth.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUserData implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;
	private Object UId;
	private String email;
	private String password;
	private String message;
	private List<GrantedAuthority> role;
	private int status;
	private String authority;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public LoginUserData() {
		super();
	}
	
	public LoginUserData(UserLoginEntity vendor) {
		this.email = vendor.getEmail();
		this.password = vendor.getPassword();
		this.role = Stream.of("USER").map(SimpleGrantedAuthority::new).collect(Collectors.toList())  ;
	}
	public LoginUserData(String token, Object uId, String email, String password, String message,
			List<GrantedAuthority> role, int status) {
		super();
		this.token = token;
		UId = uId;
		this.email = email;
		this.password = password;
		this.message = message;
		this.role = role;
		this.status = status;
		this.authority = "USER";
	}
	@Override
	public String toString() {
		return "LoginUserData [token=" + token + ", UId=" + UId + ", email=" + email + ", password=" + password
				+ ", message=" + message + ", role=" + role + ", status=" + status + "]";
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Object getUId() {
		return UId;
	}

	public void setUId(Object uId) {
		UId = uId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<GrantedAuthority> getRole() {
		return role;
	}

	public void setRole(List<GrantedAuthority> role) {
		this.role = role;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
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
