package com.divatt.admin.contoller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.specification.SpecificationEntity;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.SpecificationService;

@RestController
@RequestMapping("/specification")
public class SpecificationController {

	
	@Autowired
	private SpecificationService specificationService;
	
	
	@PostMapping("/add")
	public GlobalResponse addSpecification(@Valid @RequestBody SpecificationEntity specificationEntity)
	{
		try
		{
			return this.specificationService.addSpecification(specificationEntity);
		}
		catch(Exception e) 
		{
			throw new CustomException(e.getMessage());
		}
	}
	@GetMapping("/listOfSpecification/{categoryId}")
	public List<SpecificationEntity>listOfSpecification(@PathVariable Integer categoryId)
	{
		try
		{
			return this.specificationService.listOfSpecification(categoryId);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	
	@PutMapping("/update/{specId}")
	public GlobalResponse updateSpec(@PathVariable Integer specId,@RequestBody SpecificationEntity specificationData)
	{
		try
		{
			return this.specificationService.updateSpec(specificationData,specId);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	
	@PutMapping("/delete/{specId}")
	public GlobalResponse deleteSpec(@PathVariable Integer specId)
	{
		try
		{
			return this.specificationService.deleteSpec(specId);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
