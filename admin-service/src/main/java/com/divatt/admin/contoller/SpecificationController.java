package com.divatt.admin.contoller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	@GetMapping("/listOfSpecification/{categoryName}")
	public List<SpecificationEntity>listOfSpecification(@PathVariable String categoryName)
	{
		try
		{
			return this.specificationService.listOfSpecification(categoryName);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
