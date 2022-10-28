package com.divatt.designer.controller;

import static org.hamcrest.CoreMatchers.theInstance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.services.ProductService2;
import com.divatt.designer.services.ProductServiceImp2;

@RestController
@RequestMapping("/designerProduct")
public class ProductController2 implements ProductService2 {
	@Autowired
	private ProductServiceImp2 productServiceImp2;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController2.class);

	@Override
	@GetMapping("/list")
	public ProductMasterEntity2 getAllProduct() {
		LOGGER.info("Inside ProductController2.getAllProduct()");
		try {
			return productServiceImp2.getAllProduct();

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
}
