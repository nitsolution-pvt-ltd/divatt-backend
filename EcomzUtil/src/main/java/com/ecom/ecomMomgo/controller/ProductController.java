package com.ecom.ecomMomgo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecomMomgo.services.ProductServices;
import com.ecom.ecomMongo.model.Category;
import com.ecom.ecomMongo.model.Product;
import com.ecom.ecomMongo.repository.ProductRepository;

/**
 * @author aryansh
 *
 */

@RestController
@RequestMapping("/v1")
public class ProductController {

	@Autowired
	private ProductServices service;

	@PostMapping("/addProduct")
	public void saveProduct(@RequestBody List<Product> products) {
		service.saveProductService(products);
		System.out.println("Product saved sucessful");
	}

	
	@GetMapping("/findAllProducts")
	public ResponseEntity<?> getProducts() {
		return (ResponseEntity<?>) Optional.of(service.getAllProducts())
				.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElseThrow(() -> new RuntimeException("Could not get product"));

	}

	@GetMapping("/findProduct/{id}")
	public ResponseEntity<?> getProduct(@PathVariable int id) {

		return (ResponseEntity<?>) Optional.of(service.getProductById(id))
				.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElseThrow(() -> new RuntimeException("Could not get product"));

	}

	@GetMapping("/findProductList/{SubCatid}")
	public ResponseEntity<?> getProductsBySubCatId(@PathVariable int SubCatid){
		
		return (ResponseEntity<?>) Optional.of(service.findProductsBySubCatId(SubCatid))
				.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElseThrow(() -> new RuntimeException("Could not get product"));
	}
	


}
