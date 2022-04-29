package com.divatt.auth.entity;





import javax.annotation.Generated;
																																																																																																																																																																																																																	

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import springfox.documentation.spring.web.json.Json;


@Document(collection = "tbl_admin")
public class AdminLoginEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_admin";

	@Id
	@Field(name = "_id")
	private Object uid;
	
	@Field(name = "first_name")
	private String firstName;
	
	@Field(name = "last_name")
	private String lastName;
	
	private String email;
	
	private String password;
	
	@Field(name = "mobile_no")
	private String mobileNo;
	
	private String dob;
	
	@Field(name = "isActive")
	private boolean is_active;
	
	@Field(name = "isDeleted")
	private boolean is_deleted;
	
	private String role;
	
	@Field(name = "roel_name")
	private String roleName;
	
	@Field(name = "gender")
	private String gender;
	
	@Field(name = "auth_token")
	private String authToken;
	
	@Field(name = "created_by")
	private String createdBy;
	
	@Field(name = "created_on")
	private String createdOn;
	
	@Field(name = "modified_by")
	private String modifiedBy;
	
	
	@Field(name = "modified_on")
	private String modifiedOn;
	
	@Field(name = "profile_pic")
	private String profilePic;
	
	private Json logins;
	
	public AdminLoginEntity() {
		super();
		// TODO Auto-generated constructor stub
	}



	


	public AdminLoginEntity(Object uid, String firstName, String lastName, String email, String password,
			String mobileNo, String dob, boolean is_active, boolean is_deleted, String role, String roleName,
			String gender, String authToken, String createdBy, String createdOn, String modifiedBy, String modifiedOn,
			String profilePic, Json logins) {
		super();
		this.uid = uid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.mobileNo = mobileNo;
		this.dob = dob;
		this.is_active = is_active;
		this.is_deleted = is_deleted;
		this.role = role;
		this.roleName = roleName;
		this.gender = gender;
		this.authToken = authToken;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.modifiedBy = modifiedBy;
		this.modifiedOn = modifiedOn;
		this.profilePic = profilePic;
		this.logins = logins;
	}






	


	@Override
	public String toString() {
		return "AdminLoginEntity [uid=" + uid + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", password=" + password + ", mobileNo=" + mobileNo + ", dob=" + dob + ", is_active="
				+ is_active + ", is_deleted=" + is_deleted + ", role=" + role + ", roleName=" + roleName + ", gender="
				+ gender + ", authToken=" + authToken + ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn + ", profilePic=" + profilePic
				+ ", logins=" + logins + "]";
	}






	public String getGender() {
		return gender;
	}






	public void setGender(String gender) {
		this.gender = gender;
	}






	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public Object getUid() {
		return uid;
	}

	public void setUid(Object uid) {
		this.uid = uid;
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

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	public Json getLogins() {
		return logins;
	}

	public void setLogins(Json logins) {
		this.logins = logins;
	}



	public String getAuthToken() {
		return authToken;
	}



	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}



	public String getModifiedBy() {
		return modifiedBy;
	}



	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}



	public String getModifiedOn() {
		return modifiedOn;
	}



	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}



	public String getProfilePic() {
		return profilePic;
	}



	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}



	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	
}
