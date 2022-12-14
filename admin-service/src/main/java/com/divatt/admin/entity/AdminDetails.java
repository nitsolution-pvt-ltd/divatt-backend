package com.divatt.admin.entity;

public class AdminDetails {

	public long admin_id;
	public String name;
	public String gst_in;
	public String pan;
	public String mobile;
	public String address;

	public AdminDetails() {
		super();
	}

	public AdminDetails(long admin_id, String name, String gst_in, String pan, String mobile, String address) {
		super();
		this.admin_id = admin_id;
		this.name = name;
		this.gst_in = gst_in;
		this.pan = pan;
		this.mobile = mobile;
		this.address = address;
	}

	public long getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(long admin_id) {
		this.admin_id = admin_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGst_in() {
		return gst_in;
	}

	public void setGst_in(String gst_in) {
		this.gst_in = gst_in;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
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
	
	
	
	
}
