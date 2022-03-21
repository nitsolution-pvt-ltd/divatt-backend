package com.divatt.databaseservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.divatt.databaseservice.config.YMLConfig;

@SpringBootApplication
@EnableEurekaClient
public class DatabaseServiceDivattApplication implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseServiceDivattApplication.class);
	
	@Autowired
	YMLConfig myConfig;

	public static void main(String[] args) {
		SpringApplication.run(DatabaseServiceDivattApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		LOGGER.info("-------------------------------------");
		LOGGER.info("name : " + myConfig.getName());
		LOGGER.info("environment : " + myConfig.getEnvironment());
		LOGGER.info("contextpath : " + myConfig.getContextpath());
		LOGGER.info("servers: " + myConfig.getServers());
		LOGGER.info("-------------------------------------");
		
	}

}
