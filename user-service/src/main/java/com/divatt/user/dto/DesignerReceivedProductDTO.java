package com.divatt.user.dto;

public class DesignerReceivedProductDTO {
	
	public String comments;
	public String dateTime;
	public String courierName;
	public Boolean correctProduct;
	public String trakingNumber;
	public String image;
	public Object updatedBy;
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getCourierName() {
		return courierName;
	}
	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}
	public Boolean getCorrectProduct() {
		return correctProduct;
	}
	public void setCorrectProduct(Boolean correctProduct) {
		this.correctProduct = correctProduct;
	}
	public String getTrakingNumber() {
		return trakingNumber;
	}
	public void setTrakingNumber(String trakingNumber) {
		this.trakingNumber = trakingNumber;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Object getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Object updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	

}
