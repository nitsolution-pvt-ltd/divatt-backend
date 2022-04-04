package com.divatt.auth;

import java.lang.reflect.Method;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.divatt.auth.config.YMLConfig;
import com.divatt.auth.controller.EcomAuthContollerMethod;
import com.divatt.auth.controller.EcomAuthController;
import com.divatt.auth.exception.CustomException;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableWebMvc
@EnableSwagger2
public class EcomOauthLoginApplication implements CommandLineRunner{

	private static final Logger LOGGER = LoggerFactory.getLogger(EcomOauthLoginApplication.class);
	
	
	public static void main(String[] args) {
		SpringApplication.run(EcomOauthLoginApplication.class, args);
	}
	

	@Override
	public void run(String... args) throws Exception {
		
		EcomAuthController ecomAuthController = new EcomAuthController();
		
		
		
		if(! (ecomAuthController instanceof EcomAuthContollerMethod))
			System.exit(0);
		if(!EcomAuthContollerMethod.check())
			System.exit(0);
		
		LOGGER.info("-------------------------------------");
		LOGGER.info("name : "+myConfig.getName());
		LOGGER.info("environment : "+myConfig.getEnvironment());
		LOGGER.info("contextpath : "+myConfig.getContextpath());
		LOGGER.info("servers: "+myConfig.getServers());
		LOGGER.info("-------------------------------------");
	}
	
	
	
	@Autowired
	private YMLConfig myConfig;
	
	@Bean
	public BCryptPasswordEncoder bcryptPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
	
	@Bean
    public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("soumendolui077@gmail.com");
		mailSender.setPassword("Soumen@1234c3");
		 
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		 
		mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

}
