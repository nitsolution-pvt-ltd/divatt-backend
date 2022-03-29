package com.divatt.productservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.inject.PrivateBinder;

@Document(collection = "tbl_products")
public class ProductEntity {
	
	@Id		  
	private Integer id;
	
	private String product_name;
	private String product_description;
	private Object category;
	private Object sub_category;
	private String gender;
	private Object age;
	private Boolean cod;
	private Boolean customozation;
	
	
	

}
