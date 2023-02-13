package com.divatt.auth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import springfox.documentation.spring.web.json.Json;

@Document(collection = "tbl_designer_login")
public class DesignerLoginEntity {

	@Transient
	public static final String SEQUENCE_NAME = "tbl_designer_login";

	@Id
	private Object uid;
	
	@Field(name = "uid")
	private String uniqueId;
	
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Field(name = "password")
	private String password;
	@Field(name = "auth_token")
	private String authToken;
	@Field(name = "is_deleted")
	private Boolean isDeleted;
	@Field(name = "admin_comment")
	private String adminComment;
	@Field(name = "profile_status")
	private String profileStatus;
	@Field(name = "account_status")
	private String accountStatus;
	@Field(name = "logins")
	private Json logins;

	@Field(name = "social_type")
	private String socialType;

	@Field(name = "social_id")
	private String socialId;
	private String name;
	private Boolean isProfileCompleted;
	private String designerCurrentStatus;
	private Long productCount;
	private Long follwerCount;

	public DesignerLoginEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerLoginEntity(Object uid, String uniqueId, String email, String password, String authToken,
			Boolean isDeleted, String adminComment, String profileStatus, String accountStatus, Json logins,
			String socialType, String socialId, String name, Boolean isProfileCompleted, String designerCurrentStatus,
			Long productCount, Long follwerCount) {
		super();
		this.uid = uid;
		this.uniqueId = uniqueId;
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
		this.isProfileCompleted = isProfileCompleted;
		this.designerCurrentStatus = designerCurrentStatus;
		this.productCount = productCount;
		this.follwerCount = follwerCount;
	}

	@Override
	public String toString() {
		return "DesignerLoginEntity [uid=" + uid + ", uniqueId=" + uniqueId + ", email=" + email + ", password="
				+ password + ", authToken=" + authToken + ", isDeleted=" + isDeleted + ", adminComment=" + adminComment
				+ ", profileStatus=" + profileStatus + ", accountStatus=" + accountStatus + ", logins=" + logins
				+ ", socialType=" + socialType + ", socialId=" + socialId + ", name=" + name + ", isProfileCompleted="
				+ isProfileCompleted + ", designerCurrentStatus=" + designerCurrentStatus + ", productCount="
				+ productCount + ", follwerCount=" + follwerCount + "]";
	}

	public Object getUid() {
		return uid;
	}

	public void setUid(Object uid) {
		this.uid = uid;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
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

	public Json getLogins() {
		return logins;
	}

	public void setLogins(Json logins) {
		this.logins = logins;
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

	public Boolean getIsProfileCompleted() {
		return isProfileCompleted;
	}

	public void setIsProfileCompleted(Boolean isProfileCompleted) {
		this.isProfileCompleted = isProfileCompleted;
	}

	public String getDesignerCurrentStatus() {
		return designerCurrentStatus;
	}

	public void setDesignerCurrentStatus(String designerCurrentStatus) {
		this.designerCurrentStatus = designerCurrentStatus;
	}

	public Long getProductCount() {
		return productCount;
	}

	public void setProductCount(Long productCount) {
		this.productCount = productCount;
	}

	public Long getFollwerCount() {
		return follwerCount;
	}

	public void setFollwerCount(Long follwerCount) {
		this.follwerCount = follwerCount;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	
}
