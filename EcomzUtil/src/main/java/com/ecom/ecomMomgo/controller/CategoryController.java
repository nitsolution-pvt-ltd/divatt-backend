package com.ecom.ecomMomgo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecomMomgo.services.CategoryServices;
import com.ecom.ecomMongo.model.Category;

@RestController
@RequestMapping("/v1")
public class CategoryController {

	@Autowired
	private CategoryServices servie;

	@PostMapping("/addCategory")
	public void saveCategory(@RequestBody List<Category> catagories) {
		servie.saveCategoryService(catagories);
		System.out.println("Category Saved sucessfully");
	}

	@GetMapping(path = "/category/{catId}")
	public ResponseEntity<?> findCatagoryById(@PathVariable Integer catId) {

		return (ResponseEntity<?>) Optional.of(servie.getCategoryById(catId))
				.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElseThrow(() -> new RuntimeException("Could not get Fund events"));

	}

	@GetMapping(path = "/category/allCatagory")
	public ResponseEntity<?> findAllCatagory() {

		return (ResponseEntity<?>) Optional.of(servie.getAllCategory()).map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElseThrow(() -> new RuntimeException("Could not get Fund events"));

	}

}
