package com.divatt.admin.entity;

import java.util.List;

public class ColourEntity {

	private String color_name;
	private String color_value;
	private String is_active;
	public ColourEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ColourEntity(String color_name, String color_value, String is_active) {
		super();
		this.color_name = color_name;
		this.color_value = color_value;
		this.is_active = is_active;
	}
	@Override
	public String toString() {
		return "ColourEntity [color_name=" + color_name + ", color_value=" + color_value + ", is_active=" + is_active
				+ "]";
	}
	public String getColor_name() {
		return color_name;
	}
	public void setColor_name(String color_name) {
		this.color_name = color_name;
	}
	public String getColor_value() {
		return color_value;
	}
	public void setColor_value(String color_value) {
		this.color_value = color_value;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	
}
