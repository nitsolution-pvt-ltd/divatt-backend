package com.divatt.admin.entity;

import java.util.List;

public class ColourEntity {

	private String colorName;
	private String colorValue;
	private String isActive;
	public ColourEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ColourEntity(String colorName, String colorValue, String isActive) {
		super();
		this.colorName = colorName;
		this.colorValue = colorValue;
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "ColourEntity [colorName=" + colorName + ", colorValue=" + colorValue + ", isActive=" + isActive + "]";
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
}
