package com.divatt.designer.entity.profile;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class DesignerProfile {
	
	@NotNull
	@Field(name = "email")
	private String email;
	@NotNull
	@Field(name = "password")
	private String password;
	@NotNull
	@Field(name = "first_name")
	private String firstName;
	@NotNull
	@Field(name = "last_name")
	private String lastName;
	@NotNull
	@Field(name = "display_name")
	private String displayName;
	@NotNull
	@Field(name = "mobile_no")
	private String mobileNo;
	
	@Field(name = "alt_mobile_no")
	private String altMobileNo;
	@NotNull
	@JsonFormat(shape = Shape.STRING,pattern = "yyyy/MM/dd")
	@Field(name = "dob")
	private String dob;
	@NotNull 
	@Field(name = "gender")
	private String gender;
	@NotNull
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
