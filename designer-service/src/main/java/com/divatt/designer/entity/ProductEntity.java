package com.divatt.designer.entity;

import com.divatt.designer.entity.product.ProductMasterEntity;

public class ProductEntity {

	private ProductMasterEntity productMasterEntity;
	private Object categoryObject;
	private Object subCategoryObject;
	public ProductEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductEntity(ProductMasterEntity productMasterEntity, Object categoryObject, Object subCategoryObject) {
		super();
		this.productMasterEntity = productMasterEntity;
		this.categoryObject = categoryObject;
		this.subCategoryObject = subCategoryObject;
	}
	@Override
	public String toString() {
		return "ProductEntity [productMasterEntity=" + productMasterEntity + ", categoryObject=" + categoryObject
				+ ", subCategoryObject=" + subCategoryObject + "]";
	}
	public ProductMasterEntity getProductMasterEntity() {
		return productMasterEntity;
	}
	public void setProductMasterEntity(ProductMasterEntity productMasterEntity) {
		this.productMasterEntity = productMasterEntity;
	}
	public Object getCategoryObject() {
		return categoryObject;
	}
	public void setCategoryObject(Object categoryObject) {
		this.categoryObject = categoryObject;
	}
	public Object getSubCategoryObject() {
		return subCategoryObject;
	}
	public void setSubCategoryObject(Object subCategoryObject) {
		this.subCategoryObject = subCategoryObject;
	}
	
}
