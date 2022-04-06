package com.divatt.productservice;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.divatt.productservice.config.YMLConfig;
import com.divatt.productservice.controller.ProductController;
import com.divatt.productservice.service.ProductService;
import com.divatt.productservice.service.ProductServiceImp;

import org.slf4j.Logger;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableWebMvc
@EnableSwagger2
public class ProductServiceDivattApplication implements CommandLineRunner{

	private static final Logger LOGGER= LoggerFactory.getLogger(ProductServiceDivattApplication.class);
	
	
	@Autowired
	private YMLConfig ymlConfig;
	public static void main(String[] args) {
		SpringApplication.run(ProductServiceDivattApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ProductController productController= new ProductController();
		
		if(!(productController instanceof ProductServiceImp))
		{
			System.exit(0);
		}
		
		if(! ProductServiceImp.check())
		{
			System.exit(0);
		}
		LOGGER.info("-------------------------------------");
		LOGGER.info("name : " + ymlConfig.getName());
		LOGGER.info("environment : " + ymlConfig.getEnvironment());
		LOGGER.info("contextpath : " + ymlConfig.getContextPath());
		LOGGER.info("servers: " + ymlConfig.getServers());
		LOGGER.info("-------------------------------------");
	}
}
