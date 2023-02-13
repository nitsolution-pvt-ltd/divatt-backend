package com.divatt.designer.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.entity.product.ProductCustomizationEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.ProductCustomizationService;

@RestController
@RequestMapping("/productChart")
public class ProductCustomizationController {

	@Autowired
	private ProductCustomizationService customizationService;
	
	@PostMapping("/addChart")
	public GlobalResponce addChart(@RequestBody ProductCustomizationEntity productCustomizationEntity)
	{
		try {
			return this.customizationService.addCahrtService(productCustomizationEntity);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/view/{productName}")
	public ProductCustomizationEntity viewChart(@PathVariable String productName)
	{
		try {
			return this.customizationService.viewChartService(productName);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	
	@PutMapping("/update/{productName}")
	public GlobalResponce updateChartDetails(@PathVariable String productName, @RequestBody ProductCustomizationEntity productCustomizationEntity)
	{
		try
		{
			return this.customizationService.updateService(productName,productCustomizationEntity);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	
	@GetMapping("/getChartDetails")
	public Map<String, Object> allProductList(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "productName") String sortName,
			@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "MEN") String categoryName, 
			@RequestParam Optional<String> sortBy)
	{
		try
		{
			return this.customizationService.getChartDetails(page,limit,sort,sortName,keyword,sortBy,categoryName);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
