package com.divatt.category.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tbl_categories")
public class CategoryEntity {

	@Id
	private Long categoryId;
	private String category_name;
	private String category_description;
	private String category_image;
	private Boolean is_Active;
	private Boolean is_deleted;
	private String parent_id;
	private Long created_on;
	private String created_by;
	public CategoryEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryEntity(Long categoryId, String category_name, String category_descrition, String category_image,
			Boolean is_Active, Boolean is_deleted, String parent_id, Long created_on, String created_by) {
		super();
		this.categoryId = categoryId;
		this.category_name = category_name;
		this.category_description = category_descrition;
		this.category_image = category_image;
		this.is_Active = is_Active;
		this.is_deleted = is_deleted;
		this.parent_id = parent_id;
		this.created_on = created_on;
		this.created_by = created_by;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getCategory_description() {
		return category_description;
	}
	public void setCategory_descrition(String category_description) {
		this.category_description = category_description;
	}
	public String getCategory_image() {
		return category_image;
	}
	public void setCategory_image(String category_image) {
		this.category_image = category_image;
	}
	public Boolean getIs_Active() {
		return is_Active;
	}
	public void setIs_Active(Boolean is_Active) {
		this.is_Active = is_Active;
	}
	public Boolean getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public Long getCreated_on() {
		return created_on;
	}
	public void setCreated_on(Long created_on) {
		this.created_on = created_on;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	@Override
	public String toString() {
		return "CategoryEntity [categoryId=" + categoryId + ", category_name=" + category_name
				+ ", category_descrition=" + category_description + ", category_image=" + category_image + ", is_Active="
				+ is_Active + ", is_deleted=" + is_deleted + ", parent_id=" + parent_id + ", created_on=" + created_on
				+ ", created_by=" + created_by + "]";
	}
	
}
