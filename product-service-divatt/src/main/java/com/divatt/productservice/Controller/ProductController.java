package com.divatt.productservice.Controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.productservice.entity.ProductMasterEntity;
import com.divatt.productservice.exception.CustomException;
import com.divatt.productservice.response.GlobalResponce;
import com.divatt.productservice.service.ProductService;
import com.google.common.base.Optional;


@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	private static final Logger LOGGER=LoggerFactory.getLogger(ProductController.class);
	
	@GetMapping("/allList")
	public List<ProductMasterEntity> allList()
	{
		try
		{
			return this.productService.allList();
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
	@PostMapping("/add")
	public GlobalResponce add(@Valid @RequestBody ProductMasterEntity productEntity)
	{
		try
		{
			return productService.addData(productEntity);
		}
		catch(Exception e)
		{
			throw new CustomException(e.getMessage());
		}
	}
}
