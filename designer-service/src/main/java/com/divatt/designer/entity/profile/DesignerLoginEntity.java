package com.divatt.designer.entity.profile;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import springfox.documentation.spring.web.json.Json;

@Document(collection = "tbl_designer_login")
public class DesignerLoginEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_designer_login";

	@Id
	@NotNull
	@Field(name = "_id")
	private Long dId;
	
	@NotNull
	@Field(name = "email") private String email;
	@Field(name = "user_exist") private String userExist;
	@Field(name = "password") private String password;
	@Field(name = "auth_token") private String authToken;
	@NotNull
	@Field(name = "is_deleted") private Boolean isDeleted;
	@NotNull
	@Field(name = "profile_status") private String profileStatus;
	@Field(name = "account_status") private String accountStatus;
	
	@Field(name = "admin_comment") private String adminComment;
	
	@Field(name = "logins") private Json logins;
	
	@DBRef
	private DesignerProfileEntity designerProfileEntity;
	
	private Integer productCount = 0;
	
	
	private Integer follwerCount = 0;

	
	private String categories;
	
	private String designerCurrentStatus;

	public DesignerLoginEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public String toString() {
		return "DesignerLoginEntity [dId=" + dId + ", email=" + email + ", userExist=" + userExist + ", password="
				+ password + ", authToken=" + authToken + ", isDeleted=" + isDeleted + ", profileStatus="
				+ profileStatus + ", accountStatus=" + accountStatus + ", adminComment=" + adminComment + ", logins="
				+ logins + ", designerProfileEntity=" + designerProfileEntity + ", productCount=" + productCount
				+ ", follwerCount=" + follwerCount + ", categories=" + categories + ", designerCurrentStatus="
				+ designerCurrentStatus + "]";
	}



	public DesignerLoginEntity(@NotNull Long dId, @NotNull String email, String userExist, String password,
			String authToken, @NotNull Boolean isDeleted, @NotNull String profileStatus, String accountStatus,
			String adminComment, Json logins, DesignerProfileEntity designerProfileEntity, Integer productCount,
			Integer follwerCount, String categories, String designerCurrentStatus) {
		super();
		this.dId = dId;
		this.email = email;
		this.userExist = userExist;
		this.password = password;
		this.authToken = authToken;
		this.isDeleted = isDeleted;
		this.profileStatus = profileStatus;
		this.accountStatus = accountStatus;
		this.adminComment = adminComment;
		this.logins = logins;
		this.designerProfileEntity = designerProfileEntity;
		this.productCount = productCount;
		this.follwerCount = follwerCount;
		this.categories = categories;
		this.designerCurrentStatus = designerCurrentStatus;
	}



	public String getUserExist() {
		return userExist;
	}



	public void setUserExist(String userExist) {
		this.userExist = userExist;
	}



	public Long getdId() {
		return dId;
	}

	public void setdId(Long dId) {
		this.dId = dId;
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

	public DesignerProfileEntity getDesignerProfileEntity() {
		return designerProfileEntity;
	}

	public void setDesignerProfileEntity(DesignerProfileEntity designerProfileEntity) {
		this.designerProfileEntity = designerProfileEntity;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Integer getFollwerCount() {
		return follwerCount;
	}

	public void setFollwerCount(Integer follwerCount) {
		this.follwerCount = follwerCount;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getDesignerCurrentStatus() {
		return designerCurrentStatus;
	}

	public void setDesignerCurrentStatus(String designerCurrentStatus) {
		this.designerCurrentStatus = designerCurrentStatus;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	
}
