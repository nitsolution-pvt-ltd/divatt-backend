package com.divatt.user.entity.order;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tbl_order_details")
public class OrderDetailsEntity {
	@Transient
	public static final String SEQUENCE_NAME = "tbl_order_details";
	
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
	private Object billingAddress;
	
	@NotNull(message = "product details is required!")
	@Field(name = "products") 
	private List<Object> products;
	
	@NotNull(message = "mrp is required!")
	@Field(name = "mrp") 
	private Long mrp;
	
	@NotNull(message = "discount is required!")
	@Field(name = "discount") 
	private Long discount;
	
	@NotNull(message = "Net price is required!")
	@Field(name = "net_price") 
	private Long netPrice;
	
	@NotNull(message = "Tax amount is required!")
	@Field(name = "tax_amount") 
	private Long taxAmount;
	
	@NotNull(message = "Total amount is required!")
	@Field(name = "total_amount") 
	private Long totalAmount;
	
	@Field(name = "created_on") 
	private String createdOn;
	
	@Field(name="user_inv")
	private String userInv;

	public OrderDetailsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDetailsEntity(Integer id, @NotNull(message = "Username is required!") Long userId, String orderId,
			@NotNull(message = "Shipping address is required!") Object shippingAddress,
			@NotNull(message = "Billing address is required!") Object billingAddress,
			@NotNull(message = "product details is required!") List<Object> products,
			@NotNull(message = "mrp is required!") Long mrp, @NotNull(message = "discount is required!") Long discount,
			@NotNull(message = "Net price is required!") Long netPrice,
			@NotNull(message = "Tax amount is required!") Long taxAmount,
			@NotNull(message = "Total amount is required!") Long totalAmount, String createdOn, String userInv) {
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

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	

}
