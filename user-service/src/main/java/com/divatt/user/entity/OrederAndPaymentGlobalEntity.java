package com.divatt.user.entity;

import javax.validation.Valid;

import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;

public class OrederAndPaymentGlobalEntity {
	
	@Valid
	private OrderPaymentEntity orderPaymentEntity;
	
	@Valid
	private OrderDetailsEntity orderDetailsEntity;

	public OrederAndPaymentGlobalEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrederAndPaymentGlobalEntity(OrderPaymentEntity orderPaymentEntity, OrderDetailsEntity orderDetailsEntity) {
		super();
		this.orderPaymentEntity = orderPaymentEntity;
		this.orderDetailsEntity = orderDetailsEntity;
	}

	@Override
	public String toString() {
		return "OrederAndPaymentGlobalEntity [orderPaymentEntity=" + orderPaymentEntity + ", orderDetailsEntity="
				+ orderDetailsEntity + "]";
	}

	public OrderPaymentEntity getOrderPaymentEntity() {
		return orderPaymentEntity;
	}

	public void setOrderPaymentEntity(OrderPaymentEntity orderPaymentEntity) {
		this.orderPaymentEntity = orderPaymentEntity;
	}

	public OrderDetailsEntity getOrderDetailsEntity() {
		return orderDetailsEntity;
	}

	public void setOrderDetailsEntity(OrderDetailsEntity orderDetailsEntity) {
		this.orderDetailsEntity = orderDetailsEntity;
	}
	
	

}
