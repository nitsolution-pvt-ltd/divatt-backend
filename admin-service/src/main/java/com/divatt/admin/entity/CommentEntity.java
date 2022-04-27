package com.divatt.admin.entity;

public class CommentEntity {

	
	private Integer productId;
	private Integer designerId;
	private String comments;
	
	private String approvedBy;
	private String adminStatus;
	
	public CommentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentEntity(Integer productId, Integer designerId, String comments, String approvedBy,
			String adminStatus) {
		super();
		this.productId = productId;
		this.designerId = designerId;
		this.comments = comments;
		this.approvedBy = approvedBy;
		this.adminStatus = adminStatus;
	}

	@Override
	public String toString() {
		return "CommentEntity [productId=" + productId + ", designerId=" + designerId + ", comments=" + comments
				+ ", approvedBy=" + approvedBy + ", adminStatus=" + adminStatus + "]";
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

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}
	
	
	
}
