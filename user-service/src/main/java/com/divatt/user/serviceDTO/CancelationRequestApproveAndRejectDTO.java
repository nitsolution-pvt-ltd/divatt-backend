package com.divatt.user.serviceDTO;

public class CancelationRequestApproveAndRejectDTO {
	private String comment;
	private String orderStatus;
	
	public CancelationRequestApproveAndRejectDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CancelationRequestApproveAndRejectDTO(String comment, String orderStatus) {
		super();
		this.comment = comment;
		this.orderStatus = orderStatus;
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
	@Override
	public String toString() {
		return "CancelationRequestApproveAndRejectDTO [comment=" + comment + ", orderStatus=" + orderStatus + "]";
	}
}
