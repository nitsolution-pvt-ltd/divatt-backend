package com.divatt.user.serviceDTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class DeliveryDTO {

	private String deliveredDate;

	public DeliveryDTO() {
		super();
		// TODO Auto-generated constructor stub
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
