package com.divatt.designerregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DesignerRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesignerRegistrationApplication.class, args);
	}

}
