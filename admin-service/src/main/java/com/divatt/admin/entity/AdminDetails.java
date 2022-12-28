package com.divatt.admin.entity;

public class AdminDetails {

	public long admin_id;
	public String name;
	public String email;
	public String gst_in;
	public String pan;
	public String mobile;
	public String address;
	public String city;
	public String state;
	public String country;
	public String pin;

	public AdminDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminDetails(long admin_id, String name, String email, String gst_in, String pan, String mobile,
			String address, String city, String state, String country, String pin) {
		super();
		this.admin_id = admin_id;
		this.name = name;
		this.email = email;
		this.gst_in = gst_in;
		this.pan = pan;
		this.mobile = mobile;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pin = pin;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	@Override
	public String toString() {
		return "AdminDetails [admin_id=" + admin_id + ", name=" + name + ", email=" + email + ", gst_in=" + gst_in
				+ ", pan=" + pan + ", mobile=" + mobile + ", address=" + address + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", pin=" + pin + "]";
	}

}
