package com.divatt.user.serviceDTO;

public class CancelationRequestDTO {
	
	private String comment;
	private String orderStatus;
	public CancelationRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CancelationRequestDTO(String comment, String orderStatus) {
		super();
		this.comment = comment;
		this.orderStatus = orderStatus;
	}
	@Override
	public String toString() {
		return "CancelationRequestDTO [comment=" + comment + ", orderStatus=" + orderStatus + "]";
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
