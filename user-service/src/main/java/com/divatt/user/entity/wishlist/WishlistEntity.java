package com.divatt.user.entity.wishlist;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;


@Document(collection = "tbl_user_wishlist")
public class WishlistEntity {

	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_user_wishlist";

	@Id
	private Integer id;
	
	
	@NotNull(message = "User Name is Required!")
	@Field(value = "user_id")
	private Integer userId;
	
	
	@NotNull(message = "Product Name is Required!")
	@Field(value = "product_id")
	private Integer productId;
	
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	@Field(value = "added_on")
	private Date addedOn;

	public WishlistEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WishlistEntity(Integer id, @NotNull(message = "User Name is Required!") Integer userId,
			@NotNull(message = "Product Name is Required!") Integer productId, Date addedOn) {
		super();
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.addedOn = addedOn;
	}

	@Override
	public String toString() {
		return "WishlistEntity [id=" + id + ", userId=" + userId + ", productId=" + productId + ", addedOn=" + addedOn
				+ "]";
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

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	
	
	
	
}
