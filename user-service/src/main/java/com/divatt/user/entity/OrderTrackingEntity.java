package com.divatt.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tbl_delivery_tracking")
public class OrderTrackingEntity {

	@Transient
	public static final String SEQUENCE_NAME = "tbl_delivery_tracking";

	@Id
	private Integer id;

	@Field(name = "tracking_id")
	private String trackingId;

	@Field(name = "tracking_url")
	private String trackingUrl;

	@Field(name = "order_id")
	private String orderId;

	@Field(name = "product_id")
	private int productId;
	
	@Field(name = "user_id")
	private int userId;
	
	@Field(name = "designer_id")
	private int designerId;

	@Field(name = "procuct_sku")
	private String procuctSku;

	@Field(name = "delivery_type")
	private String deliveryType;

	@Field(name = "delivery_mode")
	private String deliveryMode;

	@Field(name = "delivery_started")
	private String deliveryStarted;

	@Field(name = "delivery_expected_date")
	private String deliveryExpectedDate;

	@Field(name = "delivery_status")
	private String deliveryStatus;

	@Field(name = "delivered_date")
	private String deliveredDate;

	@Field(name = "tracking_history")
	private List<Object> trackingHistory = new ArrayList<>();

	public OrderTrackingEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderTrackingEntity(Integer id, String trackingId, String trackingUrl, String orderId, int productId,
			int userId, int designerId, String procuctSku, String deliveryType, String deliveryMode,
			String deliveryStarted, String deliveryExpectedDate, String deliveryStatus, String deliveredDate,
			List<Object> trackingHistory) {
		super();
		this.id = id;
		this.trackingId = trackingId;
		this.trackingUrl = trackingUrl;
		this.orderId = orderId;
		this.productId = productId;
		this.userId = userId;
		this.designerId = designerId;
		this.procuctSku = procuctSku;
		this.deliveryType = deliveryType;
		this.deliveryMode = deliveryMode;
		this.deliveryStarted = deliveryStarted;
		this.deliveryExpectedDate = deliveryExpectedDate;
		this.deliveryStatus = deliveryStatus;
		this.deliveredDate = deliveredDate;
		this.trackingHistory = trackingHistory;
	}

	@Override
	public String toString() {
		return "OrderTrackingEntity [id=" + id + ", trackingId=" + trackingId + ", trackingUrl=" + trackingUrl
				+ ", orderId=" + orderId + ", productId=" + productId + ", userId=" + userId + ", designerId="
				+ designerId + ", procuctSku=" + procuctSku + ", deliveryType=" + deliveryType + ", deliveryMode="
				+ deliveryMode + ", deliveryStarted=" + deliveryStarted + ", deliveryExpectedDate="
				+ deliveryExpectedDate + ", deliveryStatus=" + deliveryStatus + ", deliveredDate=" + deliveredDate
				+ ", trackingHistory=" + trackingHistory + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getTrackingUrl() {
		return trackingUrl;
	}

	public void setTrackingUrl(String trackingUrl) {
		this.trackingUrl = trackingUrl;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDesignerId() {
		return designerId;
	}

	public void setDesignerId(int designerId) {
		this.designerId = designerId;
	}

	public String getProcuctSku() {
		return procuctSku;
	}

	public void setProcuctSku(String procuctSku) {
		this.procuctSku = procuctSku;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getDeliveryStarted() {
		return deliveryStarted;
	}

	public void setDeliveryStarted(String deliveryStarted) {
		this.deliveryStarted = deliveryStarted;
	}

	public String getDeliveryExpectedDate() {
		return deliveryExpectedDate;
	}

	public void setDeliveryExpectedDate(String deliveryExpectedDate) {
		this.deliveryExpectedDate = deliveryExpectedDate;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(String deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public List<Object> getTrackingHistory() {
		return trackingHistory;
	}

	public void setTrackingHistory(List<Object> trackingHistory) {
		this.trackingHistory = trackingHistory;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	

}
