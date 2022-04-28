package com.divatt.admin.entity.specification;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection = "tbl_product_specifications")
public class SpecificationEntity {

	@Id		  
	private Integer id;

	@Transient
	public static final String SEQUENCE_NAME = "tbl_product_specifications";
	
	@NotNull(message = "Required entity requred")
	private  Boolean required;
	@NotNull(message = "category name required")
	private String categoryName;
	@NotNull(message = "Specification name required")
	private String name;
	@NotNull(message = "Type is required")
	private String type;
	private Object option[];
	private Boolean isActive;
	private Boolean isDeleted;
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	private Date addonDate;
	public SpecificationEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SpecificationEntity(Integer id, @NotNull(message = "Required entity requred") Boolean required,
			@NotNull(message = "category name required") String categoryName,
			@NotNull(message = "Specification name required") String name,
			@NotNull(message = "Type is required") String type, Object[] option, Boolean isActive, Boolean isDeleted,
			Date addonDate) {
		super();
		this.id = id;
		this.required = required;
		this.categoryName = categoryName;
		this.name = name;
		this.type = type;
		this.option = option;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.addonDate = addonDate;
	}
	@Override
	public String toString() {
		return "SpecificationEntity [id=" + id + ", required=" + required + ", categoryName=" + categoryName + ", name="
				+ name + ", type=" + type + ", option=" + Arrays.toString(option) + ", isActive=" + isActive
				+ ", isDeleted=" + isDeleted + ", addonDate=" + addonDate + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object[] getOption() {
		return option;
	}
	public void setOption(Object[] option) {
		this.option = option;
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
	public Date getAddonDate() {
		return addonDate;
	}
	public void setAddonDate(Date addonDate) {
		this.addonDate = addonDate;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
}
