package com.divatt.user.dto;

public class DeliveryDTO {

	private String deliveredDate;

	public DeliveryDTO() {
		super();
	}

	public DeliveryDTO(String deliveredDate) {
		super();
		this.deliveredDate = deliveredDate;
	}

	public String getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(String deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	@Override
	public String toString() {
		return "DeliveryDTO [deliveredDate=" + deliveredDate + "]";
	}

}
