package com.divatt.user.entity;



import springfox.documentation.spring.web.json.Json;

public class DesignerLoginEntity {
	private Long did;
	private String email;
	private String password;
	private String authToken;
	private Boolean isDeleted;
	private String profileStatus;
	private String accountStatus;
	private String adminComment;
	private Json logins;
	
	
	private Object designerProfileEntity;
	
	private Integer productCount = 0;
	
	
	private Integer follwerCount = 0;

	
	private String categories;


	public DesignerLoginEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DesignerLoginEntity(Long did, String email, String password, String authToken, Boolean isDeleted,
			String profileStatus, String accountStatus, String adminComment, Json logins,
			Object designerProfileEntity, Integer productCount, Integer follwerCount, String categories) {
		super();
		this.did = did;
		this.email = email;
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
	}


	@Override
	public String toString() {
		return "DesignerLoginEntity [did=" + did + ", email=" + email + ", password=" + password + ", authToken="
				+ authToken + ", isDeleted=" + isDeleted + ", profileStatus=" + profileStatus + ", accountStatus="
				+ accountStatus + ", adminComment=" + adminComment + ", logins=" + logins + ", designerProfileEntity="
				+ designerProfileEntity + ", productCount=" + productCount + ", follwerCount=" + follwerCount
				+ ", categories=" + categories + "]";
	}


	public Long getDid() {
		return did;
	}


	public void setDid(Long did) {
		this.did = did;
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


	public Object getDesignerProfileEntity() {
		return designerProfileEntity;
	}


	public void setDesignerProfileEntity(Object designerProfileEntity) {
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

	
}
