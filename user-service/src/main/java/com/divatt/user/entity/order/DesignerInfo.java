package com.divatt.user.entity.order;

public class DesignerInfo {
	private Integer pin;
	private Integer mobile;
	private String country;
	private String state;
	private String city;
	private String address;
	public DesignerInfo() {
		super();
	}
	public DesignerInfo(Integer pin, Integer mobile, String country, String state, String city, String address) {
		super();
		this.pin = pin;
		this.mobile = mobile;
		this.country = country;
		this.state = state;
		this.city = city;
		this.address = address;
	}
	public Integer getPin() {
		return pin;
	}
	public void setPin(Integer pin) {
		this.pin = pin;
	}
	public Integer getMobile() {
		return mobile;
	}
	public void setMobile(Integer mobile) {
		this.mobile = mobile;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "DesignerInfo [pin=" + pin + ", mobile=" + mobile + ", country=" + country + ", state=" + state
				+ ", city=" + city + ", address=" + address + "]";
	}
}
