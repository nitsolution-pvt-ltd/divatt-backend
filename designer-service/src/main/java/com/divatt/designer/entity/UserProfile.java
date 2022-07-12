package com.divatt.designer.entity;



public class UserProfile {
	private Integer id;
	private Long userId;
	private Long designerId;
	private String comment = "";
	private Long raiting = 0l;
	private Boolean isFollowing = false;
	private String createdOn;
	public UserProfile() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserProfile(Integer id, Long userId, Long designerId, String comment, Long raiting, Boolean isFollowing,
			String createdOn) {
		super();
		this.id = id;
		this.userId = userId;
		this.designerId = designerId;
		this.comment = comment;
		this.raiting = raiting;
		this.isFollowing = isFollowing;
		this.createdOn = createdOn;
	}
	@Override
	public String toString() {
		return "UserProfile {id=" + id + ", userId=" + userId + ", designerId=" + designerId + ", comment=" + comment
				+ ", raiting=" + raiting + ", isFollowing=" + isFollowing + ", createdOn=" + createdOn + "}";
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
	public Long getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getRaiting() {
		return raiting;
	}
	public void setRaiting(Long raiting) {
		this.raiting = raiting;
	}
	public Boolean getIsFollowing() {
		return isFollowing;
	}
	public void setIsFollowing(Boolean isFollowing) {
		this.isFollowing = isFollowing;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	
}
