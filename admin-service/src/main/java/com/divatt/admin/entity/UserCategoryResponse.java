package com.divatt.admin.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class UserCategoryResponse {
  
	private Integer id;
	public static final String SEQUENCE_NAME = "tbl_categories";
	private String categoryName;
	private String categoryDescription;
	private String categoryImage;
	private Integer level;
	private Boolean isActive;
	private Boolean isDeleted;	
	private String parentId;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date createdOn;
	private String createdBy;
	private List<CategoryEntity> subCategoryEntities;
	public UserCategoryResponse() {
		super();
	}
	public UserCategoryResponse(Integer id, String categoryName, String categoryDescription, String categoryImage,
			Integer level, Boolean isActive, Boolean isDeleted, String parentId, Date createdOn, String createdBy,
			List<CategoryEntity> subCategoryEntities) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.categoryImage = categoryImage;
		this.level = level;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.parentId = parentId;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.subCategoryEntities = subCategoryEntities;
	}
	@Override
	public String toString() {
		return "UserCategoryResponse [id=" + id + ", categoryName=" + categoryName + ", categoryDescription="
				+ categoryDescription + ", categoryImage=" + categoryImage + ", level=" + level + ", isActive="
				+ isActive + ", isDeleted=" + isDeleted + ", parentId=" + parentId + ", createdOn=" + createdOn
				+ ", createdBy=" + createdBy + ", subCategoryEntities=" + subCategoryEntities + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public String getCategoryImage() {
		return categoryImage;
	}
	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public List<CategoryEntity> getSubCategoryEntities() {
		return subCategoryEntities;
	}
	public void setSubCategoryEntities(List<CategoryEntity> subCategoryEntities) {
		this.subCategoryEntities = subCategoryEntities;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
}