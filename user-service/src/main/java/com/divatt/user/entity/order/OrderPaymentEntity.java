package com.divatt.user.entity.order;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection = "tbl_order_payment_details")
public class OrderPaymentEntity {

	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_order_payment_details";

	@Id
	private Integer id;
	
	
	@Field(value = "order_id")
	private String orderId;
	
	@NotNull(message = "Username is required!")
	@Field(name = "user_id") 
	private Long userId;
	
	@NotNull(message = "Payment mode is required!")
	@Field(value = "payment_mode")
	private String paymentMode;
	
	@NotNull(message = "Payment details is required!")
	@Field(value = "payment_details")
	private Object paymentDetails;
	
	
	@NotNull(message = "Payment response is required!")
	@Field(value = "payment_response")
	private Object paymentResponse;
	
	@Field(value = "refund")
	private Object refund;
	
	@NotNull(message = "Payment status is required!")
	@Field(value = "payment_status")
	private String paymentStatus;
		
	
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd HH:mm:ss")
	@Field(value = "created_on")
	private String createdOn;


	public OrderPaymentEntity() {
		super();
	}


	public OrderPaymentEntity(Integer id, String orderId, @NotNull(message = "Username is required!") Long userId,
			@NotNull(message = "Payment mode is required!") String paymentMode,
			@NotNull(message = "Payment details is required!") Object paymentDetails,
			@NotNull(message = "Payment response is required!") Object paymentResponse, Object refund,
			@NotNull(message = "Payment status is required!") String paymentStatus, String createdOn) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.userId = userId;
		this.paymentMode = paymentMode;
		this.paymentDetails = paymentDetails;
		this.paymentResponse = paymentResponse;
		this.refund = refund;
		this.paymentStatus = paymentStatus;
		this.createdOn = createdOn;
	}


	@Override
	public String toString() {
		return "OrderPaymentEntity [id=" + id + ", orderId=" + orderId + ", userId=" + userId + ", paymentMode="
				+ paymentMode + ", paymentDetails=" + paymentDetails + ", paymentResponse=" + paymentResponse
				+ ", refund=" + refund + ", paymentStatus=" + paymentStatus + ", createdOn=" + createdOn + "]";
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getPaymentMode() {
		return paymentMode;
	}


	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}


	public Object getPaymentDetails() {
		return paymentDetails;
	}


	public void setPaymentDetails(Object paymentDetails) {
		this.paymentDetails = paymentDetails;
	}


	public Object getPaymentResponse() {
		return paymentResponse;
	}


	public void setPaymentResponse(Object paymentResponse) {
		this.paymentResponse = paymentResponse;
	}


	public Object getRefund() {
		return refund;
	}


	public void setRefund(Object refund) {
		this.refund = refund;
	}


	public String getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	public String getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}


	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}


	
}
