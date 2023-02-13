package com.divatt.user.entity;

public class DesignerDetails {
	public String GSTIN;
	public String PAN;
	public String mobile;
	public String address;
	public String boutiqueName;

	public DesignerDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerDetails(String gSTIN, String pAN, String mobile, String address, String boutiqueName) {
		super();
		GSTIN = gSTIN;
		PAN = pAN;
		this.mobile = mobile;
		this.address = address;
		this.boutiqueName = boutiqueName;
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

	@Override
	public String toString() {
		return "DesignerDetails [GSTIN=" + GSTIN + ", PAN=" + PAN + ", mobile=" + mobile + ", address=" + address
				+ ", boutiqueName=" + boutiqueName + "]";
	}
}
