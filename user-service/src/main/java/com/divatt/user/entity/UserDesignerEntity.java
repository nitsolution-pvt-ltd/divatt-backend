package com.divatt.user.entity;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tbl_user_designers")
public class UserDesignerEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_user_designers";
	
	@Id
	private Integer id;
	
	@NotNull(message = "Username is required!")
	@Field(name = "user_id") 
	private Long userId;
	@NotNull(message = "Designer name is required!")
	@Field(name = "designer_id") 
	private Long designerId;
	
	@Field(name = "comment") 
	private String comment = "";
	@Field(name = "raiting") 
	private Long raiting = 0l;
	@NotNull(message = "Following is required!")
	@Field(name = "is_following") 
	private Boolean isFollowing;
	@Field(name = "created_on") 
	private String createdOn;
	
	public UserDesignerEntity() {
		super();
	}

	public UserDesignerEntity(Integer id, @NotNull(message = "Username is required!") Long userId,
			@NotNull(message = "Designer name is required!") Long designerId, String comment, Long raiting,
			@NotNull(message = "Following is required!") Boolean isFollowing, String createdOn) {
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
		return "UserDesignerEntity [id=" + id + ", userId=" + userId + ", designerId=" + designerId + ", comment="
				+ comment + ", raiting=" + raiting + ", isFollowing=" + isFollowing + ", createdOn=" + createdOn + "]";
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

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	
	
	

}
