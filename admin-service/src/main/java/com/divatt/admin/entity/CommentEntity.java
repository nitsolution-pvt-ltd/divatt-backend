package com.divatt.admin.entity;

public class CommentEntity {

	
	private Integer productId;
	private Integer designerId;
	private String comments;
	public CommentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentEntity(Integer productId, Integer designerId, String comments) {
		super();
		this.productId = productId;
		this.designerId = designerId;
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "CommentEntity [productId=" + productId + ", designerId=" + designerId + ", comments=" + comments + "]";
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Integer designerId) {
		this.designerId = designerId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
