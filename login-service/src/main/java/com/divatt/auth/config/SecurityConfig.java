package com.divatt.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.divatt.auth.services.LoginUserDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

//	@Autowired
//	private YMLConfig myConfig;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(loginUserDetails);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().cors().and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/auth/admin/testapi").permitAll()
				.antMatchers(HttpMethod.POST, "/auth/sendMail").permitAll()
				.antMatchers(HttpMethod.POST, "/auth/sendEmailWithAttachment").permitAll()
				.antMatchers(HttpMethod.GET, "/auth/admin/testapi").permitAll()
				.antMatchers(HttpMethod.GET, "/auth/info/{role}/{id}").permitAll()
				.antMatchers(HttpMethod.POST, "/auth/login").permitAll().antMatchers(HttpMethod.POST, "/auth/add")
				.permitAll().antMatchers(HttpMethod.GET, "/auth/mailForgotPasswordLink/{user}/{email}").permitAll()
				.antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll().antMatchers(HttpMethod.GET, "/webjars/**")
				.permitAll().antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
				.antMatchers(HttpMethod.GET, "/v3/api-docs").permitAll().antMatchers(HttpMethod.GET, "/v2/api-docs")
				.permitAll().antMatchers(HttpMethod.POST, "/auth/resetPassword/{link}/{linktime}/{user}").permitAll()
				.antMatchers(HttpMethod.GET, "/auth/mailForgotPasswordLink/{email}").permitAll()
				.antMatchers(HttpMethod.GET, "/auth/Present/{role}/{email}").permitAll().antMatchers("/auth/admin/**")
				.hasAuthority("ADMIN").antMatchers("/auth/designer/**").hasAnyAuthority("DESIGNER", "ADMIN")
				.antMatchers("/auth/user/**").hasAnyAuthority("ADMIN", "USER")
				.anyRequest().authenticated().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.accessDeniedPage("/403");

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

}
