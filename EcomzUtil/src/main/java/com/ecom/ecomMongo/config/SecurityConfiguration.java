package com.ecom.ecomMongo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecom.ecomMomgo.services.UserPrincipalDetailsService;
import com.ecom.ecomMongo.repository.UserRepository;
import com.ecom.ecomMongo.model.Role;
import com.ecom.ecomMongo.jwt.JwtAuthenticationFilter;
import com.ecom.ecomMongo.jwt.JwtAuthorizationFilter;


/**
 * @author aryansh
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	
	    @Autowired
	    private UserPrincipalDetailsService userPrincipalDetailsService;
	    
	    @Autowired
	    private UserRepository userRepository;


	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) {
	        auth.authenticationProvider(authenticationProvider());
	    }  

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	                .csrf().disable()
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and()
	                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
	                .addFilter(new JwtAuthorizationFilter(authenticationManager(),  this.userRepository))
	                .authorizeRequests()
	                .antMatchers(HttpMethod.POST, "/login").permitAll()
	                .antMatchers("/**").permitAll()
					/*
					 * .antMatchers("/v1/findAllProducts").permitAll()
					 * .antMatchers("/swagger-ui.html#/").permitAll()
					 * .antMatchers("/auth/google").permitAll()
					 * .antMatchers("/auth/facebook").permitAll()
					 * .antMatchers("/v1/category/**").hasRole(Role.USR.toString())
					 * .antMatchers("/public/api/usr").hasRole(Role.USR.toString())
					 * .antMatchers("/public/api/mgradmin").hasAnyRole(Role.ADMIN.toString(),Role.
					 * MGR.toString())
					 * .antMatchers("/public/api/getAllUsers").hasRole(Role.ADMIN.toString())
					 */
	                
	                .anyRequest().authenticated();
	    }
	    
	    @Bean
	    DaoAuthenticationProvider authenticationProvider()
	    {
	        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

	        return daoAuthenticationProvider;
	    }

	    @Bean
	    PasswordEncoder passwordEncoder()
	    {
	        return new BCryptPasswordEncoder();
	    }
}
