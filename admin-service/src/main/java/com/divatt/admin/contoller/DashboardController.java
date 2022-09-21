package com.divatt.admin.contoller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.ProductService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RestTemplate restTemplate ;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);
	
	@GetMapping("/statusInformation")
	public Map<String, Object> getAllDetails() {
		
		Map<String, Object> mapStatus= new HashMap<>();

		try {
			LOGGER.info("Inside - DashboardController.getAllDetails()");
			mapStatus.put("Product", this.productService.getProductDetails());
			mapStatus.put("User", this.restTemplate.getForObject("https://localhost:8082/dev/user/userStatusInformation", Map.class));
			mapStatus.put("Designer", this.restTemplate.getForObject("https://localhost:8083/dev/designer/designerStatusInformation", Map.class));
			return mapStatus;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
