package com.divatt.designer.entity;

import javax.validation.constraints.NotNull;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(value = { "password" })
@Document(collection = "tbl_admin")
public class LoginEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_admin";

	@Id
	private Long uid;
	@NotNull(message = "User's first name must not be null")
	@Field(name = "first_name")
	private String firstName;
	@NotNull(message = "User's last name must not be null")
	@Field(name = "last_name")
	private String lastName;
//	@Email
	@NotNull(message = "User's email must not be null")
	private String email;
	
	@JsonIgnore
	@NotNull(message = "User's password must not be null")
	private String password;
//	@NumberFormat
	@NotNull(message = "User's mobile_no must not be null")
	@Field(name = "mobile_no")
	private String mobileNo;
//	@DateTimeFormat()
	@NotNull(message = "User's dob must not be null")
	private String dob;
	
	@Field(name = "is_active")
	private boolean isActive;

	@Field(value = "is_deleted")
	private boolean isDeleted;
	@NotNull(message = "User's role must not be null")
	private Long role;
	
	@Field(name = "roel_name")
	@NotNull(message = "User's role must not be null")
	private String roleName;

	@Field(name = "auth_token")
	private String authToken;

	@Field(name = "created_by")
	private String createdBy;

	@Field(name = "created_on")
	private String createdOn;

	@Field(name = "modified_by")
	private String modifiedBy;
	
	@Field(name = "gender")
	private String gender;

	@Field(name = "modified_on")
	private String modifiedOn;
	
	@NotNull(message = "User's profile_pic must not be null")
	@Field(name = "profile_pic")
	private String profilePic;
	
	private JSONObject logins;

	public LoginEntity() {
		super();
	}

	

	public LoginEntity(Long uid, @NotNull(message = "User's first name must not be null") String firstName,
			@NotNull(message = "User's last name must not be null") String lastName,
			@NotNull(message = "User's email must not be null") String email,
			@NotNull(message = "User's password must not be null") String password,
			@NotNull(message = "User's mobile_no must not be null") String mobileNo,
			@NotNull(message = "User's dob must not be null") String dob, boolean isActive, boolean isDeleted,
			@NotNull(message = "User's role must not be null") Long role,
			@NotNull(message = "User's role must not be null") String roleName, String authToken, String createdBy,
			String createdOn, String modifiedBy, String gender, String modifiedOn,
			@NotNull(message = "User's profile_pic must not be null") String profilePic, JSONObject logins) {
		super();
		this.uid = uid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.mobileNo = mobileNo;
		this.dob = dob;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.role = role;
		this.roleName = roleName;
		this.authToken = authToken;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.modifiedBy = modifiedBy;
		this.gender = gender;
		this.modifiedOn = modifiedOn;
		this.profilePic = profilePic;
		this.logins = logins;
	}



	

	@Override
	public String toString() {
		return "LoginEntity [uid=" + uid + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", mobileNo=" + mobileNo + ", dob=" + dob + ", isActive=" + isActive
				+ ", isDeleted=" + isDeleted + ", role=" + role + ", roleName=" + roleName + ", authToken=" + authToken
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", modifiedBy=" + modifiedBy + ", gender="
				+ gender + ", modifiedOn=" + modifiedOn + ", profilePic=" + profilePic + ", logins=" + logins + "]";
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public JSONObject getLogins() {
		return logins;
	}

	public void setLogins(JSONObject logins) {
		this.logins = logins;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	
	

	
	

}
