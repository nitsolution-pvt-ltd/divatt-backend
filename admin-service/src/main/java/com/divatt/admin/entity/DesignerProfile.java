package com.divatt.admin.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DesignerProfile {
	
	

	private String email;

	private String password;
	
	private String firstName1;
	
	private String lastName1;

	private String firstName2;

	
	private String lastName2;
	
	private String displayName;
	
	private String mobileNo;

	
	private String altMobileNo;

	private String digitalSignature;


	private String country;


	private String state;

	private String city;
	
	
    private String designerName;

	
	private String dob;
	
	private String gender;

	
	private String profilePic;

	
	private String designerCategory;

	
	private String pinCode;
	
	private String uid;
	
	 private String type;
	
	@Field(name="notes")
	private List<String>notes;

	public DesignerProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerProfile(String email, String password, String firstName1, String lastName1, String firstName2,
			String lastName2, String displayName, String mobileNo, String altMobileNo, String digitalSignature,
			String country, String state, String city, String designerName, String dob, String gender,
			String profilePic, String designerCategory, String pinCode, String uid, String type, List<String> notes) {
		super();
		this.email = email;
		this.password = password;
		this.firstName1 = firstName1;
		this.lastName1 = lastName1;
		this.firstName2 = firstName2;
		this.lastName2 = lastName2;
		this.displayName = displayName;
		this.mobileNo = mobileNo;
		this.altMobileNo = altMobileNo;
		this.digitalSignature = digitalSignature;
		this.country = country;
		this.state = state;
		this.city = city;
		this.designerName = designerName;
		this.dob = dob;
		this.gender = gender;
		this.profilePic = profilePic;
		this.designerCategory = designerCategory;
		this.pinCode = pinCode;
		this.uid = uid;
		this.type = type;
		this.notes = notes;
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

	public String getFirstName2() {
		return firstName2;
	}

	public void setFirstName2(String firstName2) {
		this.firstName2 = firstName2;
	}

	public String getLastName2() {
		return lastName2;
	}

	public void setLastName2(String lastName2) {
		this.lastName2 = lastName2;
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

	public String getDigitalSignature() {
		return digitalSignature;
	}

	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
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

	public String getDesignerName() {
		return designerName;
	}

	public void setDesignerName(String designerName) {
		this.designerName = designerName;
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

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getDesignerCategory() {
		return designerCategory;
	}

	public void setDesignerCategory(String designerCategory) {
		this.designerCategory = designerCategory;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "DesignerProfile [email=" + email + ", password=" + password + ", firstName1=" + firstName1
				+ ", lastName1=" + lastName1 + ", firstName2=" + firstName2 + ", lastName2=" + lastName2
				+ ", displayName=" + displayName + ", mobileNo=" + mobileNo + ", altMobileNo=" + altMobileNo
				+ ", digitalSignature=" + digitalSignature + ", country=" + country + ", state=" + state + ", city="
				+ city + ", designerName=" + designerName + ", dob=" + dob + ", gender=" + gender + ", profilePic="
				+ profilePic + ", designerCategory=" + designerCategory + ", pinCode=" + pinCode + ", uid=" + uid
				+ ", type=" + type + ", notes=" + notes + "]";
	}
	
//	@Field(name="razorpayX")
//	private RazorpayX razorpayX;

	
}
