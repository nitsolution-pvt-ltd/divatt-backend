package com.divatt.user.entity.measurement;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tbl_user_measurements")
public class UserMeasurement {
	
	@Id
	private int id;
	@NotNull(message = "UserId is required!")
	@Field(name="user_id")
	private Long userId;
	@NotNull(message = "DisplayName is required!")
	@Field(name="display_name")
	private String displayName;
	@NotNull(message = "Sizes is required!")
	@Field(name="sizes")
	private JSONObject sizes;
	@Field(name="createdOn")
	private Date createdOn;
	@Field(name="gender")
	private String gender;
	
	public UserMeasurement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserMeasurement(int id, Long userId, String displayName, JSONObject sizes, Date createdOn, String gender) {
		super();
		this.id = id;
		this.userId = userId;
		this.displayName = displayName;
		this.sizes = sizes;
		this.createdOn = createdOn;
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public JSONObject getSizes() {
		return sizes;
	}

	public void setSizes(JSONObject sizes) {
		this.sizes = sizes;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "UserMeasurement [id=" + id + ", userId=" + userId + ", displayName=" + displayName + ", sizes=" + sizes
				+ ", createdOn=" + createdOn + ", gender=" + gender + "]";
	}


}
