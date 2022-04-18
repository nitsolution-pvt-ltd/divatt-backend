package com.divatt.auth.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class DesignerProfile {
	
	@Field(name = "email")
	private String email;
	@Field(name = "password")
	private String password;
	@Field(name = "first_name")
	private String firstName;
	@Field(name = "last_name")
	private String lastName;
	@Field(name = "display_name")
	private String displayName;
	@Field(name = "mobile_no")
	private String mobileNo;
	
	@Field(name = "alt_mobile_no")
	private String altMobileNo;
	@Field(name = "dob")
	private String dob;
	@Field(name = "gender")
	private String gender;
	@Field(name = "marital_status")
	private String maritalStatus;
	@Field(name = "qualification")
	private String qualification;
	
	
	public DesignerProfile(String firstName, String lastName, String displayName, String mobileNo, String altMobileNo,
			String dob, String gender, String maritalStatus, String qualification) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.displayName = displayName;
		this.mobileNo = mobileNo;
		this.altMobileNo = altMobileNo;
		this.dob = dob;
		this.gender = gender;
		this.maritalStatus = maritalStatus;
		this.qualification = qualification;
	}


	public DesignerProfile() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "DesignerProfile [firstName=" + firstName + ", lastName=" + lastName + ", displayName=" + displayName
				+ ", mobileNo=" + mobileNo + ", altMobileNo=" + altMobileNo + ", dob=" + dob + ", gender=" + gender
				+ ", maritalStatus=" + maritalStatus + ", qualification=" + qualification + "]";
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
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


	public String getAltMobileNo() {
		return altMobileNo;
	}


	public void setAltMobileNo(String altMobileNo) {
		this.altMobileNo = altMobileNo;
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


	public String getMaritalStatus() {
		return maritalStatus;
	}


	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}


	public String getQualification() {
		return qualification;
	}


	public void setQualification(String qualification) {
		this.qualification = qualification;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
