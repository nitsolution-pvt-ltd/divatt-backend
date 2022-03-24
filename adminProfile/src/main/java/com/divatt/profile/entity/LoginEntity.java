package com.divatt.profile.entity;





import javax.annotation.Generated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.mongodb.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Document(collection = "tbl_admin")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_admin";

	@Id
	private Object uid;
	@NotNull(message = "User's first name must not be null")
	private String first_name;
	@NotNull(message = "User's last name must not be null")
	private String last_name;
	@Email
	@NotNull(message = "User's email must not be null")
	private String email;
	@NotNull(message = "User's password must not be null")
	private String password;
	@NumberFormat
	@NotNull(message = "User's mobile_no must not be null")
	private String mobile_no;
	@DateTimeFormat()
	@NotNull(message = "User's dob must not be null")
	private String dob;
	
	private boolean is_active;

	private boolean is_deleted;
	@NotNull(message = "User's role must not be null")
	private String role;

	private String auth_token;

	private String created_by;

	private String created_on;

	private String modified_by;

	private String modified_on;
	@NotNull(message = "User's profile_pic must not be null")
	private String profile_pic;

	private Object logins;
	public LoginEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	


	

	public LoginEntity(Object id, String first_name, String last_name, String email, String password, String mobile_no,
			String dob, boolean is_active, boolean is_deleted, String role, String auth_token, String created_by,
			String created_on, String modified_by, String modified_on, String profile_pic, Object logins) {
		super();
		this.uid = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
		this.mobile_no = mobile_no;
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
		return "LoginEntity [id=" + uid + ", first_name=" + first_name + ", last_name=" + last_name + ", email=" + email
				+ ", password=" + password + ", mobile_no=" + mobile_no + ", dob=" + dob + ", is_active=" + is_active
				+ ", is_deleted=" + is_deleted + ", role=" + role + ", auth_token=" + auth_token + ", created_by="
				+ created_by + ", created_on=" + created_on + ", modified_by=" + modified_by + ", modified_on="
				+ modified_on + ", profile_pic=" + profile_pic + ", logins=" + logins + "]";
	}








	public Object getId() {
		return uid;
	}








	public void setId(Object id) {
		this.uid = id;
	}








	public String getFirst_name() {
		return first_name;
	}








	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}








	public String getLast_name() {
		return last_name;
	}








	public void setLast_name(String last_name) {
		this.last_name = last_name;
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








	public String getMobile_no() {
		return mobile_no;
	}








	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
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








	public Object getLogins() {
		return logins;
	}








	public void setLogins(Object logins) {
		this.logins = logins;
	}








	



	

	
	
	

	

}
