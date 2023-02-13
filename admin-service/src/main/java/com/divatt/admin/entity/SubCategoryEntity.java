package com.divatt.admin.entity;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection="tbl_categories")
public class SubCategoryEntity {

		@Id				
		private Integer id;

		@Transient
		public static final String SEQUENCE_NAME = "tbl_categories";
		
		@NotEmpty(message = "Subcategory name is required")
		private String categoryName;
		@NotEmpty(message = "Subcategory description is required")
		private String categoryDescription;
		@NotEmpty(message = "Subcategory image is required")
		private String categoryImage;
		private Integer level;
		private Boolean isActive;
		private Boolean isDeleted;
		@NotEmpty(message = "Parent Category Name is required")
		private String parentId;
		@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
		private Date createdOn;
		private String createdBy;
		
		private SubCategoryEntity subCategory;
		
		
		public SubCategoryEntity() {
			super();
			
		}


		public SubCategoryEntity(Integer id, @NotEmpty(message = "Subcategory name is required") String categoryName,
				@NotEmpty(message = "Subcategory description is required") String categoryDescription,
				@NotEmpty(message = "Subcategory image is required") String categoryImage, Integer level,
				Boolean isActive, Boolean isDeleted,
				@NotEmpty(message = "Parent Category Name is required") String parentId, Date createdOn,
				String createdBy, SubCategoryEntity subCategory) {
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
			this.subCategory = subCategory;
		}


		@Override
		public String toString() {
			return "SubCategoryEntity [id=" + id + ", categoryName=" + categoryName + ", categoryDescription="
					+ categoryDescription + ", categoryImage=" + categoryImage + ", level=" + level + ", isActive="
					+ isActive + ", isDeleted=" + isDeleted + ", parentId=" + parentId + ", createdOn=" + createdOn
					+ ", createdBy=" + createdBy + ", subCategory=" + subCategory + "]";
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


		public SubCategoryEntity getSubCategory() {
			return subCategory;
		}


		public void setSubCategory(SubCategoryEntity subCategory) {
			this.subCategory = subCategory;
		}


		public static String getSequenceName() {
			return SEQUENCE_NAME;
		}


		
}
