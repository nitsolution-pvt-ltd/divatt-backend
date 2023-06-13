package com.divatt.user.entity.product;

public class DesignerProfile {

	private String email;

	private String displayName;

	private String mobileNo;

	private String dob;

	private String gender;

	private String country;

	private String state;

	private String city;
	
	private String digitalSignature;

	public DesignerProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerProfile(String email, String displayName, String mobileNo, String dob, String gender, String country,
			String state, String city, String digitalSignature) {
		super();
		this.email = email;
		this.displayName = displayName;
		this.mobileNo = mobileNo;
		this.dob = dob;
		this.gender = gender;
		this.country = country;
		this.state = state;
		this.city = city;
		this.digitalSignature = digitalSignature;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getDigitalSignature() {
		return digitalSignature;
	}

	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}

	@Override
	public String toString() {
		return "DesignerProfile [email=" + email + ", displayName=" + displayName + ", mobileNo=" + mobileNo + ", dob="
				+ dob + ", gender=" + gender + ", country=" + country + ", state=" + state + ", city=" + city
				+ ", digitalSignature=" + digitalSignature + "]";
	}

	
}
