package com.divatt.designer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.services.ProductServiceImp_2;
import com.divatt.designer.services.ProductService_2;

@RestController
@RequestMapping("/designerProduct")
public class ProductController_2 implements ProductServiceImp_2{
	@Autowired
	private ProductService_2 productService_2;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController_2.class);

}
