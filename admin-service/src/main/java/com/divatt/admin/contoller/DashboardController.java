package com.divatt.admin.contoller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.divatt.admin.constant.RestTemplateConstants;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.ProductService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RestTemplate restTemplate ;
	
	@Value("${DESIGNER}")
	private String DESIGNER_SERVICE;

	@Value("${AUTH}")
	private String AUTH_SERVICE;

	@Value("${ADMIN}")
	private String ADMIN_SERVICE;

	@Value("${USER}")
	private String USER_SERVICE;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);
	
	@GetMapping("/statusInformation")
	public Map<String, Object> getAllDetails() {
		
		Map<String, Object> mapStatus= new HashMap<>();

		try {
			LOGGER.info("Inside - DashboardController.getAllDetails()");
			mapStatus.put("Product", this.productService.getProductDetails());
			mapStatus.put("User", this.restTemplate.getForObject(USER_SERVICE+RestTemplateConstants.USER_STATUS_INFORMATION, Map.class));
			mapStatus.put("Designer", this.restTemplate.getForObject(DESIGNER_SERVICE+RestTemplateConstants.DESIGNER_STATUS_INFORMATION, Map.class));
			return mapStatus;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
