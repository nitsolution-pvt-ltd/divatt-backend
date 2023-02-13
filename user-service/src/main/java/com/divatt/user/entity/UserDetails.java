package com.divatt.user.entity;

public class UserDetails {

		public int userId;
	    public ShippingAddress shipping_address;
	    public BillingAddress billing_address;
	    public String mobile;
		public UserDetails() {
			super();
			// TODO Auto-generated constructor stub
		}
		public UserDetails(int userId, ShippingAddress shipping_address, BillingAddress billing_address,
				String mobile) {
			super();
			this.userId = userId;
			this.shipping_address = shipping_address;
			this.billing_address = billing_address;
			this.mobile = mobile;
		}
		@Override
		public String toString() {
			return "UserDetails [userId=" + userId + ", mobile=" + mobile + "]";
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public ShippingAddress getShipping_address() {
			return shipping_address;
		}
		public void setShipping_address(ShippingAddress shipping_address) {
			this.shipping_address = shipping_address;
		}
		public BillingAddress getBilling_address() {
			return billing_address;
		}
		public void setBilling_address(BillingAddress billing_address) {
			this.billing_address = billing_address;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
	    
}
