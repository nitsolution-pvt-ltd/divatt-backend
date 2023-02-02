package com.divatt.user.dto;

public class ReturnRequestApproveDTO {
	
	public String comments;
	public String dateTime;
	public Boolean isReturn;
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
	public Boolean getIsReturn() {
		return isReturn;
	}
	public void setIsReturn(Boolean isReturn) {
		this.isReturn = isReturn;
	}
	public Object getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Object updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	
	
	}
