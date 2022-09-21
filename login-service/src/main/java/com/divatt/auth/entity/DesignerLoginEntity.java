package com.divatt.auth.entity;

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
	private Object uid;
	
	@Field(name = "email") private String email;
	@Field(name = "password") private String password;
	@Field(name = "auth_token") private String authToken;
	@Field(name = "is_deleted") private Boolean isDeleted;
	@Field(name = "admin_comment") private String adminComment;
	@Field(name = "profile_status") private String profileStatus;
	@Field(name = "account_status") private String accountStatus;
	@Field(name = "logins") private Json logins;
	
	@Field(name = "social_type")
	private String socialType;
	
	@Field(name = "social_id")
	private String socialId ;
	private String name ;

	public DesignerLoginEntity() {
		super();
		// TODO Auto-generated constructor stub
	}





	public DesignerLoginEntity(Object uid, String email, String password, String authToken, Boolean isDeleted,
			String adminComment, String profileStatus, String accountStatus, Json logins, String socialType,
			String socialId, String name) {
		super();
		this.uid = uid;
		this.email = email;
		this.password = password;
		this.authToken = authToken;
		this.isDeleted = isDeleted;
		this.adminComment = adminComment;
		this.profileStatus = profileStatus;
		this.accountStatus = accountStatus;
		this.logins = logins;
		this.socialType = socialType;
		this.socialId = socialId;
		this.name = name;
	}





	@Override
	public String toString() {
		return "DesignerLoginEntity [uid=" + uid + ", email=" + email + ", password=" + password + ", authToken="
				+ authToken + ", isDeleted=" + isDeleted + ", adminComment=" + adminComment + ", profileStatus="
				+ profileStatus + ", accountStatus=" + accountStatus + ", logins=" + logins + ", socialType="
				+ socialType + ", socialId=" + socialId + ", name=" + name + "]";
	}





	public Object getUid() {
		return uid;
	}

	public void setUid(Object uid) {
		this.uid = uid;
	}

	public String getProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(String profileStatus) {
		this.profileStatus = profileStatus;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
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



	


	public Boolean getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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



	public String getSocialType() {
		return socialType;
	}



	public void setSocialType(String socialType) {
		this.socialType = socialType;
	}



	public String getSocialId() {
		return socialId;
	}



	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}

	
	
	
	
	

}
