package com.divatt.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.divatt.profile.config.YMLConfig;


@SpringBootApplication
public class AdminProfileApplication implements CommandLineRunner{

	
	@Autowired
	private YMLConfig myConfig;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminProfileApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(AdminProfileApplication.class, args);
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
