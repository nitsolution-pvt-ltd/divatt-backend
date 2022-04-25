package com.divatt.admin.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.exception.CustomException;
import com.divatt.admin.services.ProductService;

@RestController
//@Description("This Conttroller is responsible for Control the product service")
@RequestMapping("/product")
public class ProductController {

	
	@Autowired
	private ProductService productService;
	
	
	@PutMapping("/changeProductApprovalStatus/{productId}/{designerId}/{comment}")
	public GlobalResponse changeProductApprovalStatus(@PathVariable Integer productId,@PathVariable Integer designerId,@PathVariable String comment)
	{
		try
		{
			return this.productService.productApproval(productId,designerId,comment);
			//return null;
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
