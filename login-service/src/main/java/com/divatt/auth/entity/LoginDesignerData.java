package com.divatt.auth.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginDesignerData implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object UId;
	private String email;
	private String password;
	private String message;
	private List<GrantedAuthority> role;
	private int status;
	private String adminComment;
	private String profileStatus;
	private String token;
	private String authority;
	public LoginDesignerData() {
		super();
	}
	public LoginDesignerData(Object uId, String email, String password, String message, List<GrantedAuthority> role,
			int status, String adminComment, String profileStatus, String token, String authority) {
		super();
		UId = uId;
		this.email = email;
		this.password = password;
		this.message = message;
		this.role = role;
		this.status = status;
		this.adminComment = adminComment;
		this.profileStatus = profileStatus;
		this.token = token;
		this.authority = authority;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdminComment() {
		return adminComment;
	}
	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}
	public String getProfileStatus() {
		return profileStatus;
	}
	public void setProfileStatus(String profileStatus) {
		this.profileStatus = profileStatus;
	}
	@Override
	public String toString() {
		return "LoginDesignerData [UId=" + UId + ", email=" + email + ", password=" + password + ", message=" + message
				+ ", role=" + role + ", status=" + status + ", adminComment=" + adminComment + ", profileStatus="
				+ profileStatus + ", token=" + token + ", authority=" + authority + "]";
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("DESIGNER"));
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
