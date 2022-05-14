package com.divatt.user.entity.cart;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection = "tbl_user_cart")
public class UserCartEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_user_cart";

	@Id
	private Integer id;
	
	@NotNull(message = "Username is required!")
	@Field(value = "user_id")
	private Integer userId;
	
	
	@NotNull(message = "Product name is required!")
	@Field(value = "product_id")
	private Integer productId;
	
	@NotNull(message = "Quantity is required!")
	@Field(value = "qty")
	private Integer qty;
	
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	@Field(value = "added_on")
	private Date addedOn;

	public UserCartEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserCartEntity(Integer id, @NotNull(message = "Username is required!") Integer userId,
			@NotNull(message = "Product name is required!") Integer productId,
			@NotNull(message = "Quantity is required!") Integer qty, Date addedOn) {
		super();
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.qty = qty;
		this.addedOn = addedOn;
	}

	@Override
	public String toString() {
		return "UserCartEntity [id=" + id + ", userId=" + userId + ", productId=" + productId + ", qty=" + qty
				+ ", addedOn=" + addedOn + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}




	
}
