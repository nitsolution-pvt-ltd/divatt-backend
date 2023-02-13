package com.divatt.designer.entity;

import java.util.List;


public class OrderDetailsEntity {
	private Integer id;
	private Long userId;
	private String orderId;
	private Object shippingAddress;
	private Object billingAddress;	
	private List<Object> products;
	private Long mrp;
	private Long discount;
	private Long netPrice;
	private Long taxAmount;
	private Long totalAmount;
	private String createdOn;
	private String userInv;
	private String orderStatus;
	private String  delivaryStatus;
	public OrderDetailsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderDetailsEntity(Integer id, Long userId, String orderId, Object shippingAddress, Object billingAddress,
			List<Object> products, Long mrp, Long discount, Long netPrice, Long taxAmount, Long totalAmount,
			String createdOn, String userInv, String orderStatus, String delivaryStatus) {
		super();
		this.id = id;
		this.userId = userId;
		this.orderId = orderId;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.products = products;
		this.mrp = mrp;
		this.discount = discount;
		this.netPrice = netPrice;
		this.taxAmount = taxAmount;
		this.totalAmount = totalAmount;
		this.createdOn = createdOn;
		this.userInv = userInv;
		this.orderStatus = orderStatus;
		this.delivaryStatus = delivaryStatus;
	}
	@Override
	public String toString() {
		return "OrderDetailsEntity [id=" + id + ", userId=" + userId + ", orderId=" + orderId + ", shippingAddress="
				+ shippingAddress + ", billingAddress=" + billingAddress + ", products=" + products + ", mrp=" + mrp
				+ ", discount=" + discount + ", netPrice=" + netPrice + ", taxAmount=" + taxAmount + ", totalAmount="
				+ totalAmount + ", createdOn=" + createdOn + ", userInv=" + userInv + ", orderStatus=" + orderStatus
				+ ", delivaryStatus=" + delivaryStatus + "]";
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
	public Object getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(Object billingAddress) {
		this.billingAddress = billingAddress;
	}
	public List<Object> getProducts() {
		return products;
	}
	public void setProducts(List<Object> products) {
		this.products = products;
	}
	public Long getMrp() {
		return mrp;
	}
	public void setMrp(Long mrp) {
		this.mrp = mrp;
	}
	public Long getDiscount() {
		return discount;
	}
	public void setDiscount(Long discount) {
		this.discount = discount;
	}
	public Long getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(Long netPrice) {
		this.netPrice = netPrice;
	}
	public Long getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Long taxAmount) {
		this.taxAmount = taxAmount;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getUserInv() {
		return userInv;
	}
	public void setUserInv(String userInv) {
		this.userInv = userInv;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getDelivaryStatus() {
		return delivaryStatus;
	}
	public void setDelivaryStatus(String delivaryStatus) {
		this.delivaryStatus = delivaryStatus;
	}
	
}
