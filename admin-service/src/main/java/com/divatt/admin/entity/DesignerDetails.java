package com.divatt.admin.entity;

public class DesignerDetails {

	public long designer_id;
	public String uid;
	public String designer_name;
	public String display_name;
	public String email;
	public String gst_in;
	public String pan;
	public String mobile;
	public String address;
	public String city;
	public String state;
	public String pin;
	public String country;
	private String boutiqueName;

	public DesignerDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerDetails(long designer_id, String uid, String designer_name, String display_name, String email,
			String gst_in, String pan, String mobile, String address, String city, String state, String pin,
			String country, String boutiqueName) {
		super();
		this.designer_id = designer_id;
		this.uid = uid;
		this.designer_name = designer_name;
		this.display_name = display_name;
		this.email = email;
		this.gst_in = gst_in;
		this.pan = pan;
		this.mobile = mobile;
		this.address = address;
		this.city = city;
		this.state = state;
		this.pin = pin;
		this.country = country;
		this.boutiqueName = boutiqueName;
	}

	public long getDesigner_id() {
		return designer_id;
	}

	public void setDesigner_id(long designer_id) {
		this.designer_id = designer_id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDesigner_name() {
		return designer_name;
	}

	public void setDesigner_name(String designer_name) {
		this.designer_name = designer_name;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBoutiqueName() {
		return boutiqueName;
	}

	public void setBoutiqueName(String boutiqueName) {
		this.boutiqueName = boutiqueName;
	}

	@Override
	public String toString() {
		return "DesignerDetails [designer_id=" + designer_id + ", uid=" + uid + ", designer_name=" + designer_name
				+ ", display_name=" + display_name + ", email=" + email + ", gst_in=" + gst_in + ", pan=" + pan
				+ ", mobile=" + mobile + ", address=" + address + ", city=" + city + ", state=" + state + ", pin=" + pin
				+ ", country=" + country + ", boutiqueName=" + boutiqueName + "]";
	}

}
