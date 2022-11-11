package com.divatt.user.entity.measurement;


import java.util.Date;

import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tbl_user_measurements")
public class MeasurementEntity {

	@Transient
	public static final String SEQUENCE_NAME = "tbl_order_payment_details";
	@Id
	private Integer id;
	@Field(name ="user_id")
	private Long userId;
	@Field(name ="display_name")
	private String displyName;
	@Field(name="sizes")
	private JSONObject measurementJSON;
	@Field(name ="gender")
	private String gender;
	@Field(name ="createdOn")
	private Date createdOnDate;
	@Field(name ="size_type")
	private String sizeType;
	public MeasurementEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MeasurementEntity(Integer id, Long userId, String displyName, JSONObject measurementJSON, String gender,
			Date createdOnDate, String sizeType) {
		super();
		this.id = id;
		this.userId = userId;
		this.displyName = displyName;
		this.measurementJSON = measurementJSON;
		this.gender = gender;
		this.createdOnDate = createdOnDate;
		this.sizeType = sizeType;
	}
	@Override
	public String toString() {
		return "MeasurementEntity [id=" + id + ", userId=" + userId + ", displyName=" + displyName
				+ ", measurementJSON=" + measurementJSON + ", gender=" + gender + ", createdOnDate=" + createdOnDate
				+ ", sizeType=" + sizeType + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getDisplyName() {
		return displyName;
	}
	public void setDisplyName(String displyName) {
		this.displyName = displyName;
	}
	public JSONObject getMeasurementJSON() {
		return measurementJSON;
	}
	public void setMeasurementJSON(JSONObject measurementJSON) {
		this.measurementJSON = measurementJSON;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getCreatedOnDate() {
		return createdOnDate;
	}
	public void setCreatedOnDate(Date createdOnDate) {
		this.createdOnDate = createdOnDate;
	}
	public String getSizeType() {
		return sizeType;
	}
	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
}
