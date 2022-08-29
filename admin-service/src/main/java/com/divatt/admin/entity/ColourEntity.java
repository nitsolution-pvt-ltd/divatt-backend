package com.divatt.admin.entity;

public class ColourEntity {

	private String colorName;
	private String colorValue;
	private Boolean isActive = true;
	public ColourEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ColourEntity(String colorName, String colorValue, Boolean isActive) {
		super();
		this.colorName = colorName;
		this.colorValue = colorValue;
		this.isActive = isActive;
	}
	
	
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getColorValue() {
		return colorValue;
	}
	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "ColourEntity [colorName=" + colorName + ", colorValue=" + colorValue + ", isActive=" + isActive + "]";
	}
	
	
	
	
}
