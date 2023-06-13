package com.divatt.admin.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Field;

import com.divatt.admin.DTO.BankDetails;



public class DesignerProfileEntity {
	
	
	
	private Long designerId;
	
	
	
	private String designerName;
	
	@NotNull
	@Field(name = "mobileNo")
	private String mobileNo;
	
	
	@Field(name="type")
	 private String type;
	
	@Field(name="notes")
	private List<String> notes;
	
	@Field(name="razorpayX")
	private RazorpayX razorpayX;
	
	 private String account_type;

	
	 private DesignerProfile designerProfile;
	 

	 private DesignerPersonalInfoEntity designerPersonalInfoEntity;


	public DesignerProfileEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DesignerProfileEntity(Long designerId, String designerName, @NotNull String mobileNo, String type,
			List<String> notes, RazorpayX razorpayX, String account_type, DesignerProfile designerProfile,
			DesignerPersonalInfoEntity designerPersonalInfoEntity) {
		super();
		this.designerId = designerId;
		this.designerName = designerName;
		this.mobileNo = mobileNo;
		this.type = type;
		this.notes = notes;
		this.razorpayX = razorpayX;
		this.account_type = account_type;
		this.designerProfile = designerProfile;
		this.designerPersonalInfoEntity = designerPersonalInfoEntity;
	}


	public Long getDesignerId() {
		return designerId;
	}


	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
	}


	public String getDesignerName() {
		return designerName;
	}


	public void setDesignerName(String designerName) {
		this.designerName = designerName;
	}


	public String getMobileNo() {
		return mobileNo;
	}


	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public List<String> getNotes() {
		return notes;
	}


	public void setNotes(List<String> notes) {
		this.notes = notes;
	}


	public RazorpayX getRazorpayX() {
		return razorpayX;
	}


	public void setRazorpayX(RazorpayX razorpayX) {
		this.razorpayX = razorpayX;
	}


	public String getAccount_type() {
		return account_type;
	}


	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}


	public DesignerProfile getDesignerProfile() {
		return designerProfile;
	}


	public void setDesignerProfile(DesignerProfile designerProfile) {
		this.designerProfile = designerProfile;
	}


	public DesignerPersonalInfoEntity getDesignerPersonalInfoEntity() {
		return designerPersonalInfoEntity;
	}


	public void setDesignerPersonalInfoEntity(DesignerPersonalInfoEntity designerPersonalInfoEntity) {
		this.designerPersonalInfoEntity = designerPersonalInfoEntity;
	}


	@Override
	public String toString() {
		return "DesignerProfileEntity [designerId=" + designerId + ", designerName=" + designerName + ", mobileNo="
				+ mobileNo + ", type=" + type + ", notes=" + notes + ", razorpayX=" + razorpayX + ", account_type="
				+ account_type + ", designerProfile=" + designerProfile + ", designerPersonalInfoEntity="
				+ designerPersonalInfoEntity + "]";
	}



}
