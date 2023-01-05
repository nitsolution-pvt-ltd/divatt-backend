package com.divatt.admin.contoller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.divatt.admin.constant.RestTemplateConstant;
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
			mapStatus.put("User", this.restTemplate.getForObject(RestTemplateConstant.USER_STATUS_INFORMATION.getMessage(), Map.class));
			mapStatus.put("Designer", this.restTemplate.getForObject(RestTemplateConstant.DESIGNER_STATUS_INFORMATION.getMessage(), Map.class));
			return mapStatus;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
