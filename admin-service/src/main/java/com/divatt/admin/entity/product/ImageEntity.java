package com.divatt.admin.entity.product;

public class ImageEntity {
	private String tiny;
	private String medium;
	private String large;
	private Boolean isPrimary;
	private Integer order;
	public ImageEntity() {
		super();
	}
	public ImageEntity(String tiny, String medium, String large, Boolean isPrimary, Integer order) {
		super();
		this.tiny = tiny;
		this.medium = medium;
		this.large = large;
		this.isPrimary = isPrimary;
		this.order = order;
	}
	@Override
	public String toString() {
		return "ImageEntity [tiny=" + tiny + ", medium=" + medium + ", large=" + large + ", isPrimary=" + isPrimary
				+ ", order=" + order + "]";
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

	
}
