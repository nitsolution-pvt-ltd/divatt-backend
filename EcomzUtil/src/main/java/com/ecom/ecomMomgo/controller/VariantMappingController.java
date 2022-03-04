/**
 * 
 */
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

import com.ecom.ecomMomgo.services.VariantMappingService;
import com.ecom.ecomMongo.model.VariantMappings;

/**
 * @author situpand
 *
 */
@RestController
@RequestMapping("/v1")
public class VariantMappingController {

	@Autowired
	private VariantMappingService service;
	
	@PostMapping("/uploadVariantMapping")
	public void addVariantMappings(@RequestBody List<VariantMappings> mappings) {
		service.addVariantMappings(mappings);
		System.out.println("Saved variant mappings sucessful");	
	}
	
	@GetMapping(path = "/variantMapping/{Id}")
	public ResponseEntity<?> findCatagoryById(@PathVariable Integer Id) {

		return (ResponseEntity<?>) Optional.of(service.getVariantMappingById(Id))
				.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElseThrow(() -> new RuntimeException("Could not get Fund events"));

	}

	@GetMapping(path = "/variantMapping/allCatagory")
	public ResponseEntity<?> findAllCatagory() {

		return (ResponseEntity<?>) Optional.of(service.getAllVariantMapping()).map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElseThrow(() -> new RuntimeException("Could not get Fund events"));

	}
	
}
