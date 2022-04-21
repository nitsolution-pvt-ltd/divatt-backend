package com.divatt.designer.entity.profile;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import springfox.documentation.spring.web.json.Json;

@Document(collection = "tbl_designer_login")
public class DesignerLoginEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_designer_login";

	@Id
	@NotNull
	private Object uid;
	
	@NotNull
	@Field(name = "email") private String email;
	@Field(name = "password") private String password;
	@Field(name = "auth_token") private String authToken;
	@NotNull
	@Field(name = "is_active") private Boolean isActive;
	@NotNull
	@Field(name = "is_deleted") private Boolean isDeleted;
	@NotNull
	@Field(name = "is_approved") private Boolean isApproved;
	@NotNull
	@Field(name = "is_profile_completed") private Boolean isProfileCompleated;
	@NotNull
	@Field(name = "is_profile_submitted") private Boolean isProfileSubmitted;
	@Field(name = "admin_comment") private String adminComment;
	
	@Field(name = "logins") private Json logins;
	private DesignerProfileEntity designerProfileEntity;

	public DesignerLoginEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	



	public DesignerLoginEntity(@NotNull Object uid, @NotNull String email, String password, String authToken,
			@NotNull Boolean isActive, @NotNull Boolean isDeleted, @NotNull Boolean isApproved,
			@NotNull Boolean isProfileCompleated, @NotNull Boolean isProfileSubmitted, String adminComment, Json logins,
			DesignerProfileEntity designerProfileEntity) {
		super();
		this.uid = uid;
		this.email = email;
		this.password = password;
		this.authToken = authToken;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.isApproved = isApproved;
		this.isProfileCompleated = isProfileCompleated;
		this.isProfileSubmitted = isProfileSubmitted;
		this.adminComment = adminComment;
		this.logins = logins;
		this.designerProfileEntity = designerProfileEntity;
	}







	

	@Override
	public String toString() {
		return "DesignerLoginEntity [uid=" + uid + ", email=" + email + ", password=" + password + ", authToken="
				+ authToken + ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", isApproved=" + isApproved
				+ ", isProfileCompleated=" + isProfileCompleated + ", isProfileSubmitted=" + isProfileSubmitted
				+ ", adminComment=" + adminComment + ", logins=" + logins + ", designerProfileEntity="
				+ designerProfileEntity + "]";
	}







	public DesignerProfileEntity getDesignerProfileEntity() {
		return designerProfileEntity;
	}







	public void setDesignerProfileEntity(DesignerProfileEntity designerProfileEntity) {
		this.designerProfileEntity = designerProfileEntity;
	}







	public Object getUid() {
		return uid;
	}

	public void setUid(Object uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}



	public Boolean getIsActive() {
		return isActive;
	}



	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}



	public Boolean getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}



	public Boolean getIsApproved() {
		return isApproved;
	}



	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}



	public Boolean getIsProfileCompleated() {
		return isProfileCompleated;
	}



	public void setIsProfileCompleated(Boolean isProfileCompleated) {
		this.isProfileCompleated = isProfileCompleated;
	}



	public Boolean getIsProfileSubmitted() {
		return isProfileSubmitted;
	}



	public void setIsProfileSubmitted(Boolean isProfileSubmitted) {
		this.isProfileSubmitted = isProfileSubmitted;
	}



	public String getAdminComment() {
		return adminComment;
	}



	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}



	public Json getLogins() {
		return logins;
	}



	public void setLogins(Json logins) {
		this.logins = logins;
	}



	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	
	
	
	
	

}
