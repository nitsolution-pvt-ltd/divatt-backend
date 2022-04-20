package com.divatt.auth.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.filters.CorsFilter;
import org.apache.catalina.filters.RemoteAddrFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.divatt.auth.services.LoginUserDetails;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	
	@Autowired
	private YMLConfig myConfig;
	
//	@Bean
//	public FilterRegistrationBean remoteAddressFilter() {
//
//	    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//	    RemoteAddrFilter filter = new RemoteAddrFilter();
//
//	    filter.setAllow("27.131.209.42");
//	    filter.setDenyStatus(404);
//
//	    filterRegistrationBean.setFilter(filter);
//	    filterRegistrationBean.addUrlPatterns("/auth/login");
//
//	    return filterRegistrationBean;
//
//	}
//	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		

			
//				auth.userDetailsService(customerDetailsServices);
			
				auth.userDetailsService(loginUserDetails);
				

			
	}
	


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.
				csrf()
				.disable()
				.cors().and()
				.authorizeRequests()
//				.antMatchers("/**").hasIpAddress("192.168.29.23")
				.antMatchers(HttpMethod.GET,"/auth/admin/testapi").permitAll()
				.antMatchers(HttpMethod.POST,"/auth/sendMail").permitAll()
				.antMatchers(HttpMethod.GET,"/auth/admin/testapi").permitAll()
				.antMatchers(HttpMethod.POST,"/auth/login").permitAll()
				.antMatchers(HttpMethod.GET,"/auth/mailForgotPasswordLink/{email}").permitAll()
				.antMatchers(HttpMethod.GET,"/swagger-ui/**").permitAll()
				.antMatchers(HttpMethod.GET,"/webjars/**").permitAll()
				.antMatchers(HttpMethod.GET,"/swagger-resources/**").permitAll()
				.antMatchers(HttpMethod.GET,"/v3/api-docs").permitAll()
				.antMatchers(HttpMethod.GET,"/v2/api-docs").permitAll()
				.antMatchers(HttpMethod.POST,"/auth/resetPassword/{link}/{linktime}").permitAll()
				.antMatchers(HttpMethod.GET,"/auth/mailForgotPasswordLink/{email}").permitAll()
				.antMatchers("/auth/admin/**").hasAuthority("ADMIN")
				.antMatchers("/auth/designer/**").hasAnyAuthority("DESIGNER","ADMIN")                                                                                                                          
				.antMatchers("/auth/user/**").hasAnyAuthority("ADMIN","USER")
				.anyRequest().authenticated()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling().accessDeniedPage("/403");
		
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	
	
	
//	@Bean
//	public PasswordEncoder getPasswordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
	
	
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

}
