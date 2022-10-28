package com.divatt.designer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.services.ProductService2;
import com.divatt.designer.services.ProductServiceImp2;


@RestController
@RequestMapping("/designerProduct")
public class ProductController2 implements ProductService2{
	@Autowired
	private ProductServiceImp2 productServiceImp2;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController2.class);

}
