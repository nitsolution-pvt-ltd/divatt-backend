package com.divatt.designer.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tbl_states")
public class StateEntity {
	private Long id;
	private String name;
	private String country_code;
	private String country_name;
	private String state_code;
	private String type;
	private String latitude;
	private String longitude;
	
	public StateEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StateEntity(Long id, String name, String country_code, String country_name, String state_code, String type,
			String latitude, String longitude) {
		super();
		this.id = id;
		this.name = name;
		this.country_code = country_code;
		this.country_name = country_name;
		this.state_code = state_code;
		this.type = type;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getState_code() {
		return state_code;
	}
	public void setState_code(String state_code) {
		this.state_code = state_code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
