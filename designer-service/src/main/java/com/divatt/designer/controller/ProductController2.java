package com.divatt.designer.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.entity.product.ProductMasterEntity2;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.ProductService2;
import com.divatt.designer.services.ProductServiceImp2;

@RestController
@RequestMapping("/designerProducts")
public class ProductController2 implements ProductService2 {
	@Autowired
	private ProductServiceImp2 productServiceImp2;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController2.class);

//	@Override
//	@GetMapping("/list")
//	public ProductMasterEntity2 getAllProduct() {
//		LOGGER.info("Inside ProductController2.getAllProduct()");
//		try {
//			return productServiceImp2.getAllProduct();
//
//		} catch (Exception e) {
//			throw new CustomException(e.getMessage());
//		}
//
//	}

	@PostMapping("/addProduct")
	@Override
	public GlobalResponce addProductData(@RequestBody ProductMasterEntity2 productMasterEntity2) {
		LOGGER.info("Inside ProductController2.addProductData()");
		try {
			return this.productServiceImp2.addProductData(productMasterEntity2);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	@PutMapping("/updateProduct/{productId}")
	public GlobalResponce updateProduct(@RequestBody ProductMasterEntity2 productMasterEntity2,
			@PathVariable Integer productId) {
		try {
			LOGGER.info("Inside- ProductController2.updateProduct()");
			return productServiceImp2.updateProduct(productMasterEntity2, productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	@GetMapping("/productList")
	public List<ProductMasterEntity2> getAllProduct() {
		try {
			return productServiceImp2.getAllProduct();

		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	@GetMapping("/productList/{productId}")
	public ProductMasterEntity2 getProduct(@PathVariable Integer productId) {
		try {
			LOGGER.info("Inside- ProductController2.getProduct()");
			return productServiceImp2.getProduct(productId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
