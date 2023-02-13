package com.divatt.user.entity.order;

public class UserInfo {
	private Long postalCode;
	private String addressType;
	private String address1;
	private String address2;
	private String landmark;
	private String city;
	private String state;
	private String country;
	private Boolean primary;
	public UserInfo() {
		super();
	}
	public UserInfo(Long postalCode, String addressType, String address1, String address2, String landmark, String city,
			String state, String country, Boolean primary) {
		super();
		this.postalCode = postalCode;
		this.addressType = addressType;
		this.address1 = address1;
		this.address2 = address2;
		this.landmark = landmark;
		this.city = city;
		this.state = state;
		this.country = country;
		this.primary = primary;
	}
	public Long getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(Long postalCode) {
		this.postalCode = postalCode;
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
	@Override
	public String toString() {
		return "UserInfo [postalCode=" + postalCode + ", addressType=" + addressType + ", address1=" + address1
				+ ", address2=" + address2 + ", landmark=" + landmark + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", primary=" + primary + "]";
	}
}
