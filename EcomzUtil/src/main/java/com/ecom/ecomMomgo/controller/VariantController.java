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

import com.ecom.ecomMomgo.services.VariantService;
import com.ecom.ecomMongo.model.Variant;

/**
 * @author situpand
 *
 */
@RestController
@RequestMapping("/v1")
public class VariantController {

	@Autowired
	private VariantService service;

	@PostMapping("/uploadVariant")
	public void saveVariants(@RequestBody List<Variant> variants) {
		service.saveVariantService(variants);
		System.out.println("variant Saved sucessfully");
	}
	
	@GetMapping(path = "/variant/{varId}")
	public ResponseEntity<?> findVariantById(@PathVariable Integer varId) {

		return (ResponseEntity<?>) Optional.of(service.getVariantById(varId))
				.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElseThrow(() -> new RuntimeException("Could not get variant"+varId));

	}

	@GetMapping(path = "/variant/allvariant")
	public ResponseEntity<?> findAllVariant() {

		return (ResponseEntity<?>) Optional.of(service.getAllVariant())
				.map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElseThrow(() -> new RuntimeException("Could not get variant"));

	}
}
