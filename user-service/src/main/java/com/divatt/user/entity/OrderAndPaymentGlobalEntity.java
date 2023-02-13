package com.divatt.user.entity;

import java.util.List;

import javax.validation.Valid;

import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.order.OrderPaymentEntity;
import com.divatt.user.entity.order.OrderSKUDetailsEntity;

public class OrderAndPaymentGlobalEntity {
	
	@Valid
	private OrderPaymentEntity orderPaymentEntity;
	
	@Valid
	private OrderDetailsEntity orderDetailsEntity;
	 
	@Valid
	private List<OrderSKUDetailsEntity> orderSKUDetailsEntity;

	public OrderAndPaymentGlobalEntity() {
		super();
		// TODO Auto-generated constructor stub
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

	public List<OrderSKUDetailsEntity> getOrderSKUDetailsEntity() {
		return orderSKUDetailsEntity;
	}

	public void setOrderSKUDetailsEntity(List<OrderSKUDetailsEntity> orderSKUDetailsEntity) {
		this.orderSKUDetailsEntity = orderSKUDetailsEntity;
	}

	@Override
	public String toString() {
		return "OrderAndPaymentGlobalEntity [orderPaymentEntity=" + orderPaymentEntity + ", orderDetailsEntity="
				+ orderDetailsEntity + ", orderSKUDetailsEntity=" + orderSKUDetailsEntity + "]";
	}

}
