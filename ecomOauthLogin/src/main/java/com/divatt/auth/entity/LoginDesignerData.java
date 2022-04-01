package com.divatt.auth.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginDesignerData implements UserDetails{
	
	
	
	
	
	private Object UId;
	private String email;
	private String password;
	private String message;
	private List<GrantedAuthority> role;
	private int status;
	private Boolean isApproved;
	private Boolean isProfileCompleated;
	private Boolean isProfileSubmitted;
	private String authToken;
	
	
	
	
	

	public LoginDesignerData() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
	public LoginDesignerData(Object uId, String email, String password, String message, List<GrantedAuthority> role,
			int status, Boolean isApproved, Boolean isProfileCompleated, Boolean isProfileSubmitted, String authToken) {
		super();
		UId = uId;
		this.email = email;
		this.password = password;
		this.message = message;
		this.role = role;
		this.status = status;
		this.isApproved = isApproved;
		this.isProfileCompleated = isProfileCompleated;
		this.isProfileSubmitted = isProfileSubmitted;
		this.authToken = authToken;
	}




	public LoginDesignerData(DesignerLoginEntity vendor) {
		this.email = vendor.getEmail();
		this.password = vendor.getPassword();
		this.role = Stream.of("DESIGNER").map(SimpleGrantedAuthority::new).collect(Collectors.toList())  ;
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




	public Boolean getIsApproved() {
		return isApproved;
	}




	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}




	public Boolean getIsProfileCompleated() {
		return isProfileCompleated;
	}




	public void setIsProfileCompleated(Boolean isProfileCompleated) {
		this.isProfileCompleated = isProfileCompleated;
	}




	public Boolean getIsProfileSubmitted() {
		return isProfileSubmitted;
	}




	public void setIsProfileSubmitted(Boolean isProfileSubmitted) {
		this.isProfileSubmitted = isProfileSubmitted;
	}




	public String getAuthToken() {
		return authToken;
	}




	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	@Override
	public String toString() {
		return "LoginDesignerData [UId=" + UId + ", email=" + email + ", password=" + password + ", message=" + message
				+ ", role=" + role + ", status=" + status + ", isApproved=" + isApproved + ", isProfileCompleated="
				+ isProfileCompleated + ", isProfileSubmitted=" + isProfileSubmitted + ", authToken=" + authToken + "]";
	}




	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority("DESIGNER"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
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
