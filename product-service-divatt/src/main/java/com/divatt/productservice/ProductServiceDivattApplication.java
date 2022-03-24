package com.divatt.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProductServiceDivattApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceDivattApplication.class, args);
	}

}
