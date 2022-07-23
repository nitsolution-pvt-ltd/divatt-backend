package com.divatt.user.entity.order;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tbl_order_details_new")
public class OrderDetailsEntity {
	@Transient
	public static final String SEQUENCE_NAME = "tbl_order_details_new";
	
	@Id
	private Integer id;
	
	@NotNull(message = "Username is required!")
	@Field(name = "user_id") 
	private Long userId;
	
	@Field(name = "order_id") 
	private String orderId;
	
	@Field(name = "invoice_id") 
	private String invoiceId;
	
	@NotNull(message = "Shipping address is required!")
	@Field(name = "shipping_address") 
	private Object shippingAddress;
	
	@NotNull(message = "Billing address is required!")
	@Field(name = "billing_address") 
	private Object billingAddress;
	
	@NotNull(message = "Total amount is required!")
	@Field(name = "total_amount") 
	private Double totalAmount;	
	
	@Field(name = "order_date") 
	private String orderDate;

	@Field(name= "order_status")
	private String orderStatus;
	
	@Field(name= "delivery_status")
	private String deliveryStatus;
	
	@Field(name= "delivery_mode")
	private String deliveryMode;
	
	@Field(name = "delivery_date") 
	private String deliveryDate;
	
	@Field(name = "delivery_check_url") 
	private String deliveryCheckUrl;
	
	@Field(name = "shipping_charges") 
	private Double shippingCharges;
	
	@Field(name = "discount") 
	private Double discount;
	
	@Field(name = "mrp") 
	private Double mrp;

	@Field(name = "tax_amount") 
	private Double taxAmount;
	
	
	@Field(name = "created_on") 
	private String createdOn;

	public OrderDetailsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDetailsEntity(Integer id, @NotNull(message = "Username is required!") Long userId, String orderId,
			String invoiceId, @NotNull(message = "Shipping address is required!") Object shippingAddress,
			@NotNull(message = "Billing address is required!") Object billingAddress,
			@NotNull(message = "Total amount is required!") Double totalAmount, String orderDate, String orderStatus,
			String deliveryStatus, String deliveryMode, String deliveryDate, String deliveryCheckUrl,
			Double shippingCharges, Double discount, Double mrp, Double taxAmount, String createdOn) {
		super();
		this.id = id;
		this.userId = userId;
		this.orderId = orderId;
		this.invoiceId = invoiceId;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.totalAmount = totalAmount;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.deliveryStatus = deliveryStatus;
		this.deliveryMode = deliveryMode;
		this.deliveryDate = deliveryDate;
		this.deliveryCheckUrl = deliveryCheckUrl;
		this.shippingCharges = shippingCharges;
		this.discount = discount;
		this.mrp = mrp;
		this.taxAmount = taxAmount;
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "OrderDetailsEntity [id=" + id + ", userId=" + userId + ", orderId=" + orderId + ", invoiceId="
				+ invoiceId + ", shippingAddress=" + shippingAddress + ", billingAddress=" + billingAddress
				+ ", totalAmount=" + totalAmount + ", orderDate=" + orderDate + ", orderStatus=" + orderStatus
				+ ", deliveryStatus=" + deliveryStatus + ", deliveryMode=" + deliveryMode + ", deliveryDate="
				+ deliveryDate + ", deliveryCheckUrl=" + deliveryCheckUrl + ", shippingCharges=" + shippingCharges
				+ ", discount=" + discount + ", mrp=" + mrp + ", taxAmount=" + taxAmount + ", createdOn=" + createdOn
				+ "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Object getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Object shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Object getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Object billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryCheckUrl() {
		return deliveryCheckUrl;
	}

	public void setDeliveryCheckUrl(String deliveryCheckUrl) {
		this.deliveryCheckUrl = deliveryCheckUrl;
	}

	public Double getShippingCharges() {
		return shippingCharges;
	}

	public void setShippingCharges(Double shippingCharges) {
		this.shippingCharges = shippingCharges;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getMrp() {
		return mrp;
	}

	public void setMrp(Double mrp) {
		this.mrp = mrp;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
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
