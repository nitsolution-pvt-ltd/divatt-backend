package com.divatt.admin.contoller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.ProductMeasurementEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.ProductMeasurementService;
import com.divatt.admin.services.ProductService;

@RestController
@RequestMapping("/productMeasurement")
public class ProductMeasurementController {
	
	@Autowired
	private ProductMeasurementService productMeasurementService;

	@PostMapping("/add")
	public GlobalResponse measurementAdd(@Valid @RequestBody ProductMeasurementEntity productMeasurementEntity)
	{
		try
		{
			return this.productMeasurementService.addProductMeasurement(productMeasurementEntity);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/view/{categoryName}/{subCategoryName}")
	public ProductMeasurementEntity view (@PathVariable String categoryName,@PathVariable String subCategoryName)
	{
		try
		{
			return this.productMeasurementService.viewProductDetails(categoryName,subCategoryName);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	
}
