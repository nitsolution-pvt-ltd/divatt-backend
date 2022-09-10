package com.divatt.user.entity;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import springfox.documentation.spring.web.json.Json;

@Document(collection = "tbl_users")
public class UserLoginEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_users";
	
	@Id
	private Long id;
	@NotNull
	@Field("first_name") private String firstName;
	@NotNull
	@Field("last_name") private String lastName;
	@Field("user_exist") private String userExist;
	@NotNull
	@Field("email") private String email;
	@NotNull
	@Field("password") private String password;
	@NotNull
	@Field("mobile_no") private String mobileNo;
	@NotNull
	@Field("dob") private String dob;
	@Field("is_active") private Boolean isActive;
	@Field("is_deleted") private Boolean isDeleted;
	@Field("created_on") private String createdOn;
	@Field("profile_pic") private String profilePic;
	@Field("register_type") private String registerType;
	@Field("username") private String username;
	@Field("logins") private Json logins;
	public UserLoginEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

		
	public String getUserExist() {
		return userExist;
	}




	public void setUserExist(String userExist) {
		this.userExist = userExist;
	}




	public UserLoginEntity(Long id, @NotNull String firstName, @NotNull String lastName, String userExist,
			@NotNull String email, @NotNull String password, @NotNull String mobileNo, @NotNull String dob,
			Boolean isActive, Boolean isDeleted, String createdOn, String profilePic, String registerType,
			String username, Json logins) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userExist = userExist;
		this.email = email;
		this.password = password;
		this.mobileNo = mobileNo;
		this.dob = dob;
		this.isActive = isActive;
		this.isDeleted = isDeleted;
		this.createdOn = createdOn;
		this.profilePic = profilePic;
		this.registerType = registerType;
		this.username = username;
		this.logins = logins;
	}




	@Override
	public String toString() {
		return "UserLoginEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userExist="
				+ userExist + ", email=" + email + ", password=" + password + ", mobileNo=" + mobileNo + ", dob=" + dob
				+ ", isActive=" + isActive + ", isDeleted=" + isDeleted + ", createdOn=" + createdOn + ", profilePic="
				+ profilePic + ", registerType=" + registerType + ", username=" + username + ", logins=" + logins + "]";
	}




	public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Json getLogins() {
		return logins;
	}
	public void setLogins(Json logins) {
		this.logins = logins;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	
	

}
