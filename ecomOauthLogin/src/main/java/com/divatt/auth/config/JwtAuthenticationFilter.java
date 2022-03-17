package com.divatt.auth.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;

import com.divatt.auth.entity.LoginEntity;
import com.divatt.auth.exception.CustomException;
import com.divatt.auth.helper.JwtUtil;
import com.divatt.auth.repo.LoginRepository;
import com.divatt.auth.services.LoginUserDetails;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Controller
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private LoginUserDetails loginUserDetails;
	
	
	@Autowired
	private YMLConfig myConfig;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private LoginRepository loginRepository;
	
	
	UserDetails userDetails;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException{
		try {
			String headers = request.getHeader("Authorization");
			String username = null;
			String jwtToken = null;
			
			if (headers != null && headers.startsWith("Bearer ")) {
				jwtToken = headers.substring(7);
				
				try {
					username = this.jwtUtil.extractUsername(jwtToken);
				} catch (Exception e) {
					throw new CustomException("token is not validated");
				}
				
				
				 Optional<LoginEntity> findByUserName = loginRepository.findByEmail(username);
				 
				if (findByUserName.isPresent()) {
					this.userDetails = loginUserDetails.loadUserByUsername(username);
				} else {
					
						throw new CustomException("Token not valid");
					
				}
				
				if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
				else {
					throw new CustomException("token is not validated");
				}
				
			}
					
			
			filterChain.doFilter(request, response);
		}catch(Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType("application/json");
            response.getWriter().write("{ \"statuss\" : 500 , \"messagee\" : \""+e.getMessage()+"\"}");
            LOGGER.info(e.getMessage());
            LOGGER.info(e.toString());
		}
		
		
	}
	
	
	
}
