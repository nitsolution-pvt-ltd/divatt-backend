package com.divatt.admin.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.specification.SpecificationEntity;

public interface SpecificationService {

	public GlobalResponse addSpecification(@Valid SpecificationEntity specificationEntity);

	public List<SpecificationEntity> listOfSpecification(Integer categoryId);

	public GlobalResponse updateSpec(SpecificationEntity specificationData, Integer specId);

	public Map<String, Object> getAllSpec(int page, int limit, String sort, String sortName, Boolean isDeleted, String keyword,
			Optional<String> sortBy);

	public GlobalResponse activeSpecification(Integer specId);

	public ResponseEntity<SpecificationEntity> view(Integer specId);

	public GlobalResponse deleteSpec(Integer specId);

}
