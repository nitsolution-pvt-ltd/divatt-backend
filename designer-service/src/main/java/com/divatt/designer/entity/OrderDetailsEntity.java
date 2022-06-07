package com.divatt.designer.entity;

import java.util.List;

import com.divatt.designer.entity.product.ProductMasterEntity;

public class OrderDetailsEntity {
	private Integer id;
	private Long userId;
	private String orderId;
	private Object shippingAddress;
	private Object billingAddress;	
	private List<ProductMasterEntity> products;
	private Long mrp;
	private Long discount;
	private Long netPrice;
	private Long taxAmount;
	private Long totalAmount;
	private String createdOn;
	private String userInv;
	public OrderDetailsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderDetailsEntity(Integer id, Long userId, String orderId, Object shippingAddress, Object billingAddress,
			List<ProductMasterEntity> products, Long mrp, Long discount, Long netPrice, Long taxAmount,
			Long totalAmount, String createdOn, String userInv) {
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
	}
	@Override
	public String toString() {
		return "OrderDetailsEntity [id=" + id + ", userId=" + userId + ", orderId=" + orderId + ", shippingAddress="
				+ shippingAddress + ", billingAddress=" + billingAddress + ", products=" + products + ", mrp=" + mrp
				+ ", discount=" + discount + ", netPrice=" + netPrice + ", taxAmount=" + taxAmount + ", totalAmount="
				+ totalAmount + ", createdOn=" + createdOn + ", userInv=" + userInv + "]";
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
	public List<ProductMasterEntity> getProducts() {
		return products;
	}
	public void setProducts(List<ProductMasterEntity> products) {
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
	
}
