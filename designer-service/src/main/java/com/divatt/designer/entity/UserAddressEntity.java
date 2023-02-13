package com.divatt.designer.entity;

public class UserAddressEntity {
	private Long id;
	private Long userId;
	private String fullName;
	private String email;
	private int postalCode;
	private String mobile;
	private String addressType;
	private String address1;
	private String address2;
	private String landmark;
	private String city;
	private String state;
	private String country;
	private Boolean primary;
	private String createdOn;
	public UserAddressEntity() {
		super();
	}
	public UserAddressEntity(Long id, Long userId, String fullName, String email, int postalCode, String mobile,
			String addressType, String address1, String address2, String landmark, String city, String state,
			String country, Boolean primary, String createdOn) {
		super();
		this.id = id;
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.postalCode = postalCode;
		this.mobile = mobile;
		this.addressType = addressType;
		this.address1 = address1;
		this.address2 = address2;
		this.landmark = landmark;
		this.city = city;
		this.state = state;
		this.country = country;
		this.primary = primary;
		this.createdOn = createdOn;
	}
	@Override
	public String toString() {
		return "UserAddressEntity [id=" + id + ", userId=" + userId + ", fullName=" + fullName + ", email=" + email
				+ ", postalCode=" + postalCode + ", mobile=" + mobile + ", addressType=" + addressType + ", address1="
				+ address1 + ", address2=" + address2 + ", landmark=" + landmark + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", primary=" + primary + ", createdOn=" + createdOn + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Boolean getPrimary() {
		return primary;
	}
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	
}
