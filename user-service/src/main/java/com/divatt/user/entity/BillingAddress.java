package com.divatt.user.entity;

public class BillingAddress {

	 public String country;
	    public String address2;
	    public String city;
	    public String address1;
	    public String postalCode;
	    public String mobile;
	    public String fullName;
	    public String state;
		public BillingAddress() {
			super();
			// TODO Auto-generated constructor stub
		}
		public BillingAddress(String country, String address2, String city, String address1, String postalCode,
				String mobile, String fullName, String state) {
			super();
			this.country = country;
			this.address2 = address2;
			this.city = city;
			this.address1 = address1;
			this.postalCode = postalCode;
			this.mobile = mobile;
			this.fullName = fullName;
			this.state = state;
		}
		@Override
		public String toString() {
			return "BillingAddress [country=" + country + ", address2=" + address2 + ", city=" + city + ", address1="
					+ address1 + ", postalCode=" + postalCode + ", mobile=" + mobile + ", fullName=" + fullName
					+ ", state=" + state + "]";
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
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
	    
}
