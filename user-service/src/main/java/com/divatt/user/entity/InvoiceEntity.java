package com.divatt.user.entity;

import org.springframework.stereotype.Service;

import com.divatt.user.entity.order.OrderDetailsEntity;
import com.divatt.user.entity.orderPayment.OrderPaymentEntity;
@Service
public class InvoiceEntity {

	private UserLoginEntity userEntity;
	private OrderPaymentEntity orderPaymentEntity;
	private OrderDetailsEntity orderDetailsEntity;
	public InvoiceEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InvoiceEntity(UserLoginEntity userEntity, OrderPaymentEntity orderPaymentEntity,
			OrderDetailsEntity orderDetailsEntity) {
		super();
		this.userEntity = userEntity;
		this.orderPaymentEntity = orderPaymentEntity;
		this.orderDetailsEntity = orderDetailsEntity;
	}
	@Override
	public String toString() {
		return "InvoiceEntity [userEntity=" + userEntity + ", orderPaymentEntity=" + orderPaymentEntity
				+ ", orderDetailsEntity=" + orderDetailsEntity + "]";
	}
	public UserLoginEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserLoginEntity userEntity) {
		this.userEntity = userEntity;
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
