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
	@Field(name = "first_name1")
	private String firstName1;
	@NotNull
	@Field(name = "last_name1")
	private String lastName1;

	@Field(name = "first_name2")
	private String firstName2;

	@Field(name = "last_name2")
	private String lastName2;
	@NotNull
	@Field(name = "display_name")
	private String displayName;
	@NotNull
	@Field(name = "mobile_no")
	private String mobileNo;

	@Field(name = "alt_mobile_no")
	private String altMobileNo;

	@Field(name = "digital_signature")
	private String digitalSignature;

	@Field(name = "country")
	private String country;

	@Field(name = "state")
	private String state;

	@Field(name = "city")
	private String city;

	@NotNull
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy/MM/dd")

	@Field(name = "dob")
	private String dob;
	@NotNull

	@Field(name = "gender")
	private String gender;

	@NotNull(message = "User profile picture must not be null")
	@Field(name = "profile_pic")
	private String profilePic;

	@Field(name = "designer_category")
	private String designerCategory;

	@Field(name = "pin_code")
	private String pinCode;

	public DesignerProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerProfile(@NotNull String email, @NotNull String password, @NotNull String firstName1,
			@NotNull String lastName1, String firstName2, String lastName2, @NotNull String displayName,
			@NotNull String mobileNo, String altMobileNo, String digitalSignature, String country, String state,
			String city, @NotNull String dob, @NotNull String gender,
			@NotNull(message = "User profile picture must not be null") String profilePic, String designerCategory,
			String pinCode) {
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
		this.dob = dob;
		this.gender = gender;
		this.profilePic = profilePic;
		this.designerCategory = designerCategory;
		this.pinCode = pinCode;
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

	@Override
	public String toString() {
		return "DesignerProfile [email=" + email + ", password=" + password + ", firstName1=" + firstName1
				+ ", lastName1=" + lastName1 + ", firstName2=" + firstName2 + ", lastName2=" + lastName2
				+ ", displayName=" + displayName + ", mobileNo=" + mobileNo + ", altMobileNo=" + altMobileNo
				+ ", digitalSignature=" + digitalSignature + ", country=" + country + ", state=" + state + ", city="
				+ city + ", dob=" + dob + ", gender=" + gender + ", profilePic=" + profilePic + ", designerCategory="
				+ designerCategory + ", pinCode=" + pinCode + "]";
	}

}
