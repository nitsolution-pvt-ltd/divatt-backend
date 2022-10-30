package com.divatt.designer.services;

import java.util.Map;
import java.util.Optional;

import com.divatt.designer.entity.product.ProductCustomizationEntity;
import com.divatt.designer.response.GlobalResponce;

public interface ProductCustomizationService {

	GlobalResponce addCahrtService(ProductCustomizationEntity productCustomizationEntity);

	ProductCustomizationEntity viewChartService(String productName);

	GlobalResponce updateService(String productName, ProductCustomizationEntity productCustomizationEntity);

	Map<String, Object> getChartDetails(int page, int limit, String sort, String sortName, String keyword,
			Optional<String> sortBy, String categoryName);

}
