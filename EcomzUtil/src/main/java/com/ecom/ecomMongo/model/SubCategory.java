package com.ecom.ecomMongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SubCategory")
public class SubCategory {

	private Integer categoryId;
	@Id
	private Integer subCategoryId;
	private String subCategoryName;
	private String subCategoryDescription;
	private String status;
	private Integer mandatoryAttribute;
	private String attribute;
	private Integer preferedAttribute;
	private Integer indexingAttribute;
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public String getSubCategoryDescription() {
		return subCategoryDescription;
	}
	public void setSubCategoryDescription(String subCategoryDescription) {
		this.subCategoryDescription = subCategoryDescription;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getMandatoryAttribute() {
		return mandatoryAttribute;
	}
	public void setMandatoryAttribute(Integer mandatoryAttribute) {
		this.mandatoryAttribute = mandatoryAttribute;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public Integer getPreferedAttribute() {
		return preferedAttribute;
	}
	public void setPreferedAttribute(Integer preferedAttribute) {
		this.preferedAttribute = preferedAttribute;
	}
	public Integer getIndexingAttribute() {
		return indexingAttribute;
	}
	public void setIndexingAttribute(Integer indexingAttribute) {
		this.indexingAttribute = indexingAttribute;
	}

	
}
