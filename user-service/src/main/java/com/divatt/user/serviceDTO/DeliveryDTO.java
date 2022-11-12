package com.divatt.user.serviceDTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class DeliveryDTO {

	private String deliveryDate;

	public DeliveryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeliveryDTO(String deliveryDate) {
		super();
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Override
	public String toString() {
		return "DeliveryDTO [deliveryDate=" + deliveryDate + "]";
	}

}
