package com.divatt.user.entity;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tbl_user_addresses")
public class UserAddressEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_user_addresses";
	
	@Id
	private Long id;
	
	@NotNull(message = "Username is required!")
	@Field(name = "user_id") 
	private Long userId;
	
	@NotNull(message = "FullName is required!")
	@Field(name = "full_name") private String fullName;
	
	@NotNull(message = "mobile is required!")
	@Field(name = "mobile") private String mobile;
	
	@NotNull(message = "address1 is required!")
	@Field(name = "address1") private String address1;
	
	@NotNull(message = "address2 is required!")
	@Field(name = "address2") private String address2;
	
	@NotNull(message = "landmark is required!")
	@Field(name = "landmark") private String landmark;
	
	@NotNull(message = "city is required!")
	@Field(name = "city") private String city;
	
	@NotNull(message = "state is required!")
	@Field(name = "state") private String state;
	
	@NotNull(message = "country is required!")
	@Field(name = "country") private String country;
	
	@NotNull(message = "primary is required!")
	@Field(name = "primary") private Boolean primary;

	@Field(name = "created_on") private String createdOn;

	public UserAddressEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserAddressEntity(Long id, @NotNull(message = "Username is required!") Long userId,
			@NotNull(message = "FullName is required!") String fullName,
			@NotNull(message = "mobile is required!") String mobile,
			@NotNull(message = "address1 is required!") String address1,
			@NotNull(message = "address2 is required!") String address2,
			@NotNull(message = "landmark is required!") String landmark,
			@NotNull(message = "city is required!") String city, @NotNull(message = "state is required!") String state,
			@NotNull(message = "country is required!") String country, Boolean primary, String createdOn) {
		super();
		this.id = id;
		this.userId = userId;
		this.fullName = fullName;
		this.mobile = mobile;
		this.address1 = address1;
		this.address2 = address2;
		this.landmark = landmark;
		this.city = city;
		this.state = state;
		this.country = country;
		this.primary = primary;
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "UserAddressEntity [id=" + id + ", userId=" + userId + ", fullName=" + fullName + ", mobile=" + mobile
				+ ", address1=" + address1 + ", address2=" + address2 + ", landmark=" + landmark + ", city=" + city
				+ ", state=" + state + ", country=" + country + ", primary=" + primary + ", createdOn=" + createdOn
				+ "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getPrimary() {
		return primary;
	}

	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	
	

}
