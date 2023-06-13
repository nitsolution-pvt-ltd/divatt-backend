package com.divatt.user.entity;

public class DesignerDetails {
	public String GSTIN;
	public String PAN;
	public String mobile;
	public String address;
	public String boutiqueName;
	public String city="";
	public String state="";
	
	public DesignerDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerDetails(String gSTIN, String pAN, String mobile, String address, String boutiqueName, String city,
			String state) {
		super();
		GSTIN = gSTIN;
		PAN = pAN;
		this.mobile = mobile;
		this.address = address;
		this.boutiqueName = boutiqueName;
		this.city = city;
		this.state = state;
	}

	public String getGSTIN() {
		return GSTIN;
	}

	public void setGSTIN(String gSTIN) {
		GSTIN = gSTIN;
	}

	public String getPAN() {
		return PAN;
	}

	public void setPAN(String pAN) {
		PAN = pAN;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBoutiqueName() {
		return boutiqueName;
	}

	public void setBoutiqueName(String boutiqueName) {
		this.boutiqueName = boutiqueName;
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

	@Override
	public String toString() {
		return "DesignerDetails [GSTIN=" + GSTIN + ", PAN=" + PAN + ", mobile=" + mobile + ", address=" + address
				+ ", boutiqueName=" + boutiqueName + ", city=" + city + ", state=" + state + "]";
	}

}
