package com.divatt.user.entity.order;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.divatt.user.entity.BillingAddressEntity;

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
	
	@NotNull(message = "Shipping address is required!")
	@Field(name = "shipping_address") 
	private Object shippingAddress;
	
	@NotNull(message = "Billing address is required!")
	@Field(name = "billing_address") 
	private BillingAddressEntity billingAddress;
	
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
	
	@Field(name = "cgst") 
	private Double cgst;
	
	@Field(name = "sgst") 
	private Double sgst;
	
	@Field(name = "igst") 
	private Double igst;
	
	@Field(name = "shipping_charge") 
	private Double shippingCharge;
	
	@Field(name = "shipping_cgst") 
	private Double shippingCGST;
	
	@Field(name = "shipping_sgst") 
	private Double shippingSGST;
	
	@Field(name = "shipping_igst") 
	private Double shippingIGST;
	
	@Field(name = "created_on") 
	private String createdOn;
	
	@Field(name = "razorpay_order_id") 
	private String razorpayOrderId;

	public OrderDetailsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDetailsEntity(Integer id, @NotNull(message = "Username is required!") Long userId, String orderId,
			@NotNull(message = "Shipping address is required!") Object shippingAddress,
			@NotNull(message = "Billing address is required!") BillingAddressEntity billingAddress,
			@NotNull(message = "Total amount is required!") Double totalAmount, String orderDate, String orderStatus,
			String deliveryStatus, String deliveryMode, String deliveryDate, String deliveryCheckUrl,
			Double shippingCharges, Double discount, Double mrp, Double taxAmount, Double cgst, Double sgst,
			Double igst, Double shippingCharge, Double shippingCGST, Double shippingSGST, Double shippingIGST,
			String createdOn, String razorpayOrderId) {
		super();
		this.id = id;
		this.userId = userId;
		this.orderId = orderId;
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
		this.cgst = cgst;
		this.sgst = sgst;
		this.igst = igst;
		this.shippingCharge = shippingCharge;
		this.shippingCGST = shippingCGST;
		this.shippingSGST = shippingSGST;
		this.shippingIGST = shippingIGST;
		this.createdOn = createdOn;
		this.razorpayOrderId = razorpayOrderId;
	}

	@Override
	public String toString() {
		return "OrderDetailsEntity [id=" + id + ", userId=" + userId + ", orderId=" + orderId + ", shippingAddress="
				+ shippingAddress + ", billingAddress=" + billingAddress + ", totalAmount=" + totalAmount
				+ ", orderDate=" + orderDate + ", orderStatus=" + orderStatus + ", deliveryStatus=" + deliveryStatus
				+ ", deliveryMode=" + deliveryMode + ", deliveryDate=" + deliveryDate + ", deliveryCheckUrl="
				+ deliveryCheckUrl + ", shippingCharges=" + shippingCharges + ", discount=" + discount + ", mrp=" + mrp
				+ ", taxAmount=" + taxAmount + ", cgst=" + cgst + ", sgst=" + sgst + ", igst=" + igst
				+ ", shippingCharge=" + shippingCharge + ", shippingCGST=" + shippingCGST + ", shippingSGST="
				+ shippingSGST + ", shippingIGST=" + shippingIGST + ", createdOn=" + createdOn + ", razorpayOrderId="
				+ razorpayOrderId + "]";
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

	public Object getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Object shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public BillingAddressEntity getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(BillingAddressEntity billingAddress) {
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

	public Double getCgst() {
		return cgst;
	}

	public void setCgst(Double cgst) {
		this.cgst = cgst;
	}

	public Double getSgst() {
		return sgst;
	}

	public void setSgst(Double sgst) {
		this.sgst = sgst;
	}

	public Double getIgst() {
		return igst;
	}

	public void setIgst(Double igst) {
		this.igst = igst;
	}

	public Double getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(Double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	public Double getShippingCGST() {
		return shippingCGST;
	}

	public void setShippingCGST(Double shippingCGST) {
		this.shippingCGST = shippingCGST;
	}

	public Double getShippingSGST() {
		return shippingSGST;
	}

	public void setShippingSGST(Double shippingSGST) {
		this.shippingSGST = shippingSGST;
	}

	public Double getShippingIGST() {
		return shippingIGST;
	}

	public void setShippingIGST(Double shippingIGST) {
		this.shippingIGST = shippingIGST;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}

	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	

	
	
}
