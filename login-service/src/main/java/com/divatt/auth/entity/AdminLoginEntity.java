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
	
	@Field(name = "authToken")
	private String auth_token;
	
	@Field(name = "createdBy")
	private String created_by;
	
	@Field(name = "createdOn")
	private String created_on;
	
	@Field(name = "modifiedBy")
	private String modified_by;
	
	
	@Field(name = "modifiedOn")
	private String modified_on;
	
	@Field(name = "profilePic")
	private String profile_pic;
	
	private Json logins;
	
	public AdminLoginEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	


	

	








	



	public AdminLoginEntity(Object uid, String firstName, String lastName, String email, String password,
			String mobileNo, String dob, boolean is_active, boolean is_deleted, String role, String auth_token,
			String created_by, String created_on, String modified_by, String modified_on, String profile_pic,
			Json logins) {
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
		this.auth_token = auth_token;
		this.created_by = created_by;
		this.created_on = created_on;
		this.modified_by = modified_by;
		this.modified_on = modified_on;
		this.profile_pic = profile_pic;
		this.logins = logins;
	}





















	








	@Override
	public String toString() {
		return "AdminLoginEntity [uid=" + uid + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", password=" + password + ", mobileNo=" + mobileNo + ", dob=" + dob + ", is_active="
				+ is_active + ", is_deleted=" + is_deleted + ", role=" + role + ", auth_token=" + auth_token
				+ ", created_by=" + created_by + ", created_on=" + created_on + ", modified_by=" + modified_by
				+ ", modified_on=" + modified_on + ", profile_pic=" + profile_pic + ", logins=" + logins + "]";
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





















	public String getAuth_token() {
		return auth_token;
	}





















	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}





















	public String getCreated_by() {
		return created_by;
	}





















	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}





















	public String getCreated_on() {
		return created_on;
	}





















	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}





















	public String getModified_by() {
		return modified_by;
	}





















	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}





















	public String getModified_on() {
		return modified_on;
	}





















	public void setModified_on(String modified_on) {
		this.modified_on = modified_on;
	}





















	public String getProfile_pic() {
		return profile_pic;
	}





















	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
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
