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

import com.ecom.ecomMomgo.services.SpecificationService;
import com.ecom.ecomMongo.model.Specification;

@RestController
@RequestMapping("/v1")
public class SpecificationController {

	@Autowired
	private SpecificationService services;

	@PostMapping("/addSpecification")
	public void saveSubCategory(@RequestBody List<Specification> specs) {
		services.saveSpecificationService(specs);
		System.out.println("specification saved sucefully");
	}

	@GetMapping(path = "/specification/{specId}")
	public ResponseEntity<?> findCatagoryById(@PathVariable Integer specId) {

		return (ResponseEntity<?>) Optional.of(services.getSpecificationById(specId))
				.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElseThrow(() -> new RuntimeException("Could not get specification"));

	}

	@GetMapping(path = "/specification/allspecification")
	public ResponseEntity<?> findAllSubCategory() {

		return (ResponseEntity<?>) Optional.of(services.getAllSpecifications())
				.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElseThrow(() -> new RuntimeException("Could not get specification"));

	}

}
