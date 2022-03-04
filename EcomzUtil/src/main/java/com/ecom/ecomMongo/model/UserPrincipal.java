package com.ecom.ecomMongo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecom.ecomMongo.model.User;

/**
 * @author aryansh
 *
 */
public class UserPrincipal implements UserDetails{
	
	
	private static final long serialVersionUID = -6267246152682344025L;
	
	
	private User user;
	
	public UserPrincipal(User user){
        this.user = user;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		 List<GrantedAuthority> authorities = new ArrayList<>();
		    // Extract list of roles (ROLE)
	        this.user.getRoleList().forEach(role -> {
	            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
	            authorities.add(authority);
	        });
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUserName();
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
		return user.isActive();
	}

}
