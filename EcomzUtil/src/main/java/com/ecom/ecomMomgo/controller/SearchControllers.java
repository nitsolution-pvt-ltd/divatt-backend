package com.ecom.ecomMomgo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecomMomgo.constants.DivattConstants;
import com.ecom.ecomMomgo.services.ProductServices;
import com.ecom.ecomMongo.config.SearchConfig;
import com.ecom.ecomMongo.model.Product;
@RestController
@RequestMapping("/v1")
public class SearchControllers {
	@Autowired
	ProductServices productServices;
	private final Logger logger = LoggerFactory.getLogger(SearchControllers.class);
	@Autowired
	private SearchConfig searchConfig;
	
	@GetMapping(path = "/fund/search")
	public ResponseEntity<?> getFundBySearchCriteria(
			@RequestParam(value = "filter", defaultValue = "prodId::1") String filter,
			@RequestParam(value = "range", defaultValue = "NA") String range,
			@RequestParam(value = "limit", defaultValue = "10") Integer limit,
			@RequestParam(value = "offset", defaultValue = "0") Integer offset) {

		List<Product> products = null;
		if ("NA".equals(range)) {
			Map<String, Object> filterMap = getSearchCriteria(filter);
			products = productServices.getProdctsBySearchCriteria(filterMap, limit, offset);
		} else {
		}
		

		return Optional.of(products).map(e -> new ResponseEntity<>(e, HttpStatus.OK))
				.orElseThrow(() -> new RuntimeException("No Products available for the criteria"));
	}
	
	
	
	
	
	
	@GetMapping("/getProductBySearch/{keyword}")
	public List<Product> getProductsByProductDesc(@PathVariable String keyword) {
		return productServices.findBySearch(keyword);
	}
	private Map<String, Object> getSearchCriteria(String filterQuery) {
		Map<String, Object> filterMap = new HashMap<>();
		String[] filters = filterQuery.split("\\|");
		for (String filter : filters) {
			if (filter.split(DivattConstants.SEARCH_DELIMITER).length > 1) {
				String key = filter.split(DivattConstants.SEARCH_DELIMITER)[0];
				String value = filter.split(DivattConstants.SEARCH_DELIMITER)[1];
				logger.info("Search key value " + key + "-" + value);

				if (searchConfig.getSearchFields().contains(key)) {
					filterMap.put(key, value);
				} else if (searchConfig.getSearchLongFields().contains(key)) {
					filterMap.put(key, Long.parseLong(value));
				} else if ("versionNumber".equalsIgnoreCase(key)) {
					filterMap.put("versionNumber", Long.parseLong(value));
				} else {
					throw new RuntimeException("Invalid search field specified.");
				}
			}

		}
		logger.info(filterMap.toString());
		return filterMap;

	}
	
	

}
