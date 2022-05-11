package com.divatt.admin.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import nonapi.io.github.classgraph.json.Id;

@Document(collection = "tbl_admin_mdata")
public class ProductMeasurementEntity {


	@Transient
	public static final String SEQUENCE_NAME = "tbl_admin_mdata";
	
	@Id
	//@Field(name="_id")
	private Integer id;
	
//	@Field(name = "meta_key")
	private String metaKey;
	
//	@Field(name = "type")
	private String categoryName;
	
//	@Field(name = "category")
	private String subCategoryName;
	
//	@Field(name = "MEASUREMENT_LIST")
	private Object measurementKey;
	
	private String imageString;

	public ProductMeasurementEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductMeasurementEntity(Integer id, String metaKey, String categoryName, String subCategoryName,
			Object measurementKey, String imageString) {
		super();
		this.id = id;
		this.metaKey = metaKey;
		this.categoryName = categoryName;
		this.subCategoryName = subCategoryName;
		this.measurementKey = measurementKey;
		this.imageString = imageString;
	}

	@Override
	public String toString() {
		return "ProductMeasurementEntity [id=" + id + ", metaKey=" + metaKey + ", categoryName=" + categoryName
				+ ", subCategoryName=" + subCategoryName + ", measurementKey=" + measurementKey + ", imageString="
				+ imageString + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMetaKey() {
		return metaKey;
	}

	public void setMetaKey(String metaKey) {
		this.metaKey = metaKey;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public Object getMeasurementKey() {
		return measurementKey;
	}

	public void setMeasurementKey(Object measurementKey) {
		this.measurementKey = measurementKey;
	}

	public String getImageString() {
		return imageString;
	}

	public void setImageString(String imageString) {
		this.imageString = imageString;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}


}
