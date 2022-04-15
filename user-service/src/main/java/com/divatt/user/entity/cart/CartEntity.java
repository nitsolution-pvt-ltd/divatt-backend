package com.divatt.user.entity.cart;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tbl_user_cart")
public class CartEntity {
	
	
	@Field(value = "_id")
	private Integer cartId;
	@NotNull(message = "User Id required")
	@Field(value = "user_id")
	private Integer userId;
	
	@NotNull(message = "Product Id must not be null")
	@Field(value = "product_Id") 
	private Integer productId;
	
	@NotNull(message = "Product Quantity should be mention")
	@Field(value = "qty")
	private Integer productQty;
	
	//@NotNull(message = "")
	@Field(value = "added_on")
	private Date addedDate;
	public CartEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartEntity(Integer cartId, Integer userId, Integer productId, Integer productQty, Date addedDate) {
		super();
		this.cartId = cartId;
		this.userId = userId;
		this.productId = productId;
		this.productQty = productQty;
		this.addedDate = addedDate;
	}
	@Override
	public String toString() {
		return "CartEntity [cartId=" + cartId + ", userId=" + userId + ", productId=" + productId + ", productQty="
				+ productQty + ", addedDate=" + addedDate + "]";
	}
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getProductQty() {
		return productQty;
	}
	public void setProductQty(Integer productQty) {
		this.productQty = productQty;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	
}
