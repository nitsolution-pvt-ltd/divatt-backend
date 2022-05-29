package com.divatt.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.divatt.user.config.YMLConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
public class UserApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
	
	@Autowired
	private YMLConfig myConfig;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(UserApplication.class);
	
	@Bean
	public BCryptPasswordEncoder bcryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
	
	@Override
	public void run(String... args) throws Exception {
		
	
		
		LOGGER.info("-------------------------------------");
		LOGGER.info("name : "+myConfig.getName());
		LOGGER.info("environment : "+myConfig.getEnvironment());
		LOGGER.info("contextpath : "+myConfig.getContextpath());
		LOGGER.info("servers: "+myConfig.getServers());
		LOGGER.info("-------------------------------------");
	}

}
