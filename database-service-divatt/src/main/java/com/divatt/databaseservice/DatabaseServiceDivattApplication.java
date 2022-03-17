package com.divatt.databaseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DatabaseServiceDivattApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseServiceDivattApplication.class, args);
	}

}
