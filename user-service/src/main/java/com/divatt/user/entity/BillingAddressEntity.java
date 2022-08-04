package com.divatt.user.entity;

public class BillingAddressEntity {
	private String address1;
	private String address2;
	private String country;
	private String state;
	private String city;
	private String postalCode;
	private String mobile;
	private String fullName;
	public BillingAddressEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BillingAddressEntity(String address1, String address2, String country, String state, String city,
			String postalCode, String mobile, String fullName) {
		super();
		this.address1 = address1;
		this.address2 = address2;
		this.country = country;
		this.state = state;
		this.city = city;
		this.postalCode = postalCode;
		this.mobile = mobile;
		this.fullName = fullName;
	}
	@Override
	public String toString() {
		return "BillingAddressEntity [address1=" + address1 + ", address2=" + address2 + ", country=" + country
				+ ", state=" + state + ", city=" + city + ", postalCode=" + postalCode + ", mobile=" + mobile
				+ ", fullName=" + fullName + "]";
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
