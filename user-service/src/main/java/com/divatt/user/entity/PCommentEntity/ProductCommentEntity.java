package com.divatt.user.entity.PCommentEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Document(collection = "tbl_product_comments")
public class ProductCommentEntity<E> {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_product_comments";

	@Id
	private Integer id;
	
	@NotNull(message = "Username is Required!")
	@Field(value = "user_id")
	private Integer userId;
		
	@NotNull(message = "Product Name is Required!")
	@Field(value = "product_id")
	private Integer productId;
	
	@NotNull(message = "Rating is Required!")
	@Field(value = "rating")
	private Float rating;
	
	@NotNull(message = "Comment is Required!")
	@Field(value = "comment")
	private String comment;
		
	private List<Object> uploads =new ArrayList<>();
	 
	
	@Field(value = "is_visible")
	private Boolean isVisible;
	
	
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	@Field(value = "created_on")
	private Date createdOn;


	public ProductCommentEntity() {
		super();
	}


	public ProductCommentEntity(Integer id, @NotNull(message = "Username is Required!") Integer userId,
			@NotNull(message = "Product Name is Required!") Integer productId,
			@NotNull(message = "Rating is Required!") Float rating,
			@NotNull(message = "Comment is Required!") String comment, List<Object> uploads, Boolean isVisible,
			Date createdOn) {
		super();
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.rating = rating;
		this.comment = comment;
		this.uploads = uploads;
		this.isVisible = isVisible;
		this.createdOn = createdOn;
	}


	@Override
	public String toString() {
		return "productCommentEntity [id=" + id + ", userId=" + userId + ", productId=" + productId + ", rating="
				+ rating + ", comment=" + comment + ", uploads=" + uploads + ", isVisible=" + isVisible + ", createdOn="
				+ createdOn + "]";
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


	public Float getRating() {
		return rating;
	}


	public void setRating(Float rating) {
		this.rating = rating;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public List<Object> getUploads() {
		return uploads;
	}


	public void setUploads(List<Object> uploads) {
		this.uploads = uploads;
	}


	public Boolean getIsVisible() {
		return isVisible;
	}


	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	

}
