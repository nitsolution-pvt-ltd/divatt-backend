package com.divatt.designer.entity.product;

public class ImagesEntity {
	private String name;
	private String tiny;
	private String medium;
	private String large;
	private Boolean isPrimary;
	private Integer order;
	private String colour;
	public ImagesEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ImagesEntity(String name, String tiny, String medium, String large, Boolean isPrimary, Integer order,
			String colour) {
		super();
		this.name = name;
		this.tiny = tiny;
		this.medium = medium;
		this.large = large;
		this.isPrimary = isPrimary;
		this.order = order;
		this.colour = colour;
	}
	@Override
	public String toString() {
		return "ImagesEntity [name=" + name + ", tiny=" + tiny + ", medium=" + medium + ", large=" + large
				+ ", isPrimary=" + isPrimary + ", order=" + order + ", colour=" + colour + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTiny() {
		return tiny;
	}
	public void setTiny(String tiny) {
		this.tiny = tiny;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getLarge() {
		return large;
	}
	public void setLarge(String large) {
		this.large = large;
	}
	public Boolean getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
}
