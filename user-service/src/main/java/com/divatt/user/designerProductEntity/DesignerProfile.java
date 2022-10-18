package com.divatt.user.designerProductEntity;

public class DesignerProfile {

	private String email;

	private String displayName;

	private String mobileNo;

	private String dob;

	private String gender;

	public DesignerProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerProfile(String email, String displayName, String mobileNo, String dob, String gender) {
		super();
		this.email = email;
		this.displayName = displayName;
		this.mobileNo = mobileNo;
		this.dob = dob;
		this.gender = gender;
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

	@Override
	public String toString() {
		return "DesignerProfile [email=" + email + ", displayName=" + displayName + ", mobileNo=" + mobileNo + ", dob="
				+ dob + ", gender=" + gender + "]";
	}

}
