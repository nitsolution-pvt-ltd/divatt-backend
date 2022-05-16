package com.divatt.designer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
