package com.divatt.admin.entity;

public class UserResponseEntity {

	private CategoryEntity categoryEntity;
	private SubCategoryEntity subCategoryEntity;
	public UserResponseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserResponseEntity(CategoryEntity categoryEntity, SubCategoryEntity subCategoryEntity) {
		super();
		this.categoryEntity = categoryEntity;
		this.subCategoryEntity = subCategoryEntity;
	}
	@Override
	public String toString() {
		return "UserResponseEntity [categoryEntity=" + categoryEntity + ", subCategoryEntity=" + subCategoryEntity
				+ "]";
	}
	public CategoryEntity getCategoryEntity() {
		return categoryEntity;
	}
	public void setCategoryEntity(CategoryEntity categoryEntity) {
		this.categoryEntity = categoryEntity;
	}
	public SubCategoryEntity getSubCategoryEntity() {
		return subCategoryEntity;
	}
	public void setSubCategoryEntity(SubCategoryEntity subCategoryEntity) {
		this.subCategoryEntity = subCategoryEntity;
	}
	
}
