package com.divatt.user.dto;

public class ForceReturnOnDTO {

	public String comments;
	public String dateTime;
	public boolean returnAcceptable;
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
	public Object getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Object updatedBy) {
		this.updatedBy = updatedBy;
	}
	public boolean isReturnAcceptable() {
		return returnAcceptable;
	}
	public void setReturnAcceptable(boolean returnAcceptable) {
		this.returnAcceptable = returnAcceptable;
	}
	
	
	
	
	
	
}
