package com.ecom.ecomMongo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchConfig {
	
	@Value("#{'${divatt.search.allowedFields}'.split(',')}")
	private List<String> searchFields;
	
	@Value("#{'${divatt.search.longTypes}'.split(',')}")
	private List<String> searchLongFields;

	public List<String> getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(List<String> searchFields) {
		this.searchFields = searchFields;
	}

	public List<String> getSearchLongFields() {
		return searchLongFields;
	}

	public void setSearchLongFields(List<String> searchLongFields) {
		this.searchLongFields = searchLongFields;
	}

}
