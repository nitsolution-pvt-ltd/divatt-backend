package com.divatt.admin.services;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.divatt.admin.entity.GlobalResponse;
import com.divatt.admin.entity.ProductMeasurementEntity;

public interface ProductMeasurementService {

	GlobalResponse addProductMeasurement(@Valid ProductMeasurementEntity productMeasurementEntity);

	ProductMeasurementEntity viewProductDetails(String categoryName, String subCategoryName);

	Map<String, Object> getSpecificationDetails(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy, Boolean isDelete, String metaKey);

	GlobalResponse deletemeasurementService(String metaKey, Integer id);

	GlobalResponse MeasurementUpdateService(@Valid ProductMeasurementEntity productMeasurementEntity,
			Integer measurementId);

	GlobalResponse updateStatus(String metaKey, Integer id);

}
