package com.divatt.auth.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class DesignerProfile {

	@Field(name = "email")
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Field(name = "password")
	private String password;
	@Field(name = "first_name1")
	private String firstName1;
	@Field(name = "last_name1")
	private String lastName1;
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

	public DesignerProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerProfile(String email, String password, String firstName1, String lastName1, String displayName,
			String mobileNo, String altMobileNo, String dob, String gender, String maritalStatus,
			String qualification) {
		super();
		this.email = email;
		this.password = password;
		this.firstName1 = firstName1;
		this.lastName1 = lastName1;
		this.displayName = displayName;
		this.mobileNo = mobileNo;
		this.altMobileNo = altMobileNo;
		this.dob = dob;
		this.gender = gender;
		this.maritalStatus = maritalStatus;
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

	public String getFirstName1() {
		return firstName1;
	}

	public void setFirstName1(String firstName1) {
		this.firstName1 = firstName1;
	}

	public String getLastName1() {
		return lastName1;
	}

	public void setLastName1(String lastName1) {
		this.lastName1 = lastName1;
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

	@Override
	public String toString() {
		return "DesignerProfile [email=" + email + ", password=" + password + ", firstName1=" + firstName1
				+ ", lastName1=" + lastName1 + ", displayName=" + displayName + ", mobileNo=" + mobileNo
				+ ", altMobileNo=" + altMobileNo + ", dob=" + dob + ", gender=" + gender + ", maritalStatus="
				+ maritalStatus + ", qualification=" + qualification + "]";
	}

}
