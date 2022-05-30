package com.divatt.admin.contoller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.ProductMeasurementEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.ProductMeasurementService;
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

	
	@GetMapping("/getAllMeasurementData")
	public Map<String, Object> getAllMeasurementData(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "DESC") String sort,
			@RequestParam(defaultValue = "categoryName") String sortName,
			@RequestParam(defaultValue = "false") Boolean isDelete,
			@RequestParam(defaultValue = "PRODUCT_MEASUREMENTS") String metaKey, @RequestParam(defaultValue = "") String keyword,
			@RequestParam Optional<String> sortBy){
		try {
			return this.productMeasurementService.getSpecificationDetails(page,limit,sort,sortName,keyword,sortBy,isDelete,metaKey);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@PutMapping("/updateStatus/{metaKey}/{id}")
	public GlobalResponse updateStatus(@PathVariable String metaKey, @PathVariable Integer id)
	{
		try {
			return this.productMeasurementService.updateStatus(metaKey, id);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@PutMapping("/deleteMeasurement/{metaKey}/{id}")
	public GlobalResponse deleteMeasurment(@PathVariable String metaKey, @PathVariable Integer id)
	{
		try {
			return this.productMeasurementService.deletemeasurementService(metaKey, id);
		}
		catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
