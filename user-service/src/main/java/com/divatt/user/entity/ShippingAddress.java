package com.divatt.user.entity;

public class ShippingAddress {

	 public String country;
	    public String address2;
	    public String city;
	    public String address1;
	    public int postalCode;
	    public String mobile;
	    public String fullName;
	    public String state;
	    public String landmark;
	    public String email;
		public ShippingAddress() {
			super();
			// TODO Auto-generated constructor stub
		}
		public ShippingAddress(String country, String address2, String city, String address1, int postalCode,
				String mobile, String fullName, String state, String landmark, String email) {
			super();
			this.country = country;
			this.address2 = address2;
			this.city = city;
			this.address1 = address1;
			this.postalCode = postalCode;
			this.mobile = mobile;
			this.fullName = fullName;
			this.state = state;
			this.landmark = landmark;
			this.email = email;
		}
		@Override
		public String toString() {
			return "ShippingAddress [country=" + country + ", address2=" + address2 + ", city=" + city + ", address1="
					+ address1 + ", postalCode=" + postalCode + ", mobile=" + mobile + ", fullName=" + fullName
					+ ", state=" + state + ", landmark=" + landmark + ", email=" + email + "]";
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getAddress2() {
			return address2;
		}
		public void setAddress2(String address2) {
			this.address2 = address2;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getAddress1() {
			return address1;
		}
		public void setAddress1(String address1) {
			this.address1 = address1;
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
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getLandmark() {
			return landmark;
		}
		public void setLandmark(String landmark) {
			this.landmark = landmark;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	    
}
