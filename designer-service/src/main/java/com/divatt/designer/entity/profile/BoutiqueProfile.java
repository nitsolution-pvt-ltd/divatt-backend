package com.divatt.designer.entity.profile;

import org.springframework.data.mongodb.core.mapping.Field;

public class BoutiqueProfile {
	
	@Field(name = "firm_name")
	private String firmName;
	@Field(name = "boutique_name")
	private String boutiqueName;
	@Field(name = "operating_city")
	private String operatingCity;
	@Field(name = "professional_category")
	private String professionalCategory;
	@Field(name = "year_of_operation")
	private String yearOfOperation;
	@Field(name = "experience")
	private String experience;
	@Field(name = "GSTIN")
	private String GSTIN;
	public BoutiqueProfile() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoutiqueProfile(String firmName, String boutiqueName, String operatingCity, String professionalCategory,
			String yearOfOperation, String experience, String gSTIN) {
		super();
		this.firmName = firmName;
		this.boutiqueName = boutiqueName;
		this.operatingCity = operatingCity;
		this.professionalCategory = professionalCategory;
		this.yearOfOperation = yearOfOperation;
		this.experience = experience;
		GSTIN = gSTIN;
	}
	@Override
	public String toString() {
		return "BoutiqueProfile [firmName=" + firmName + ", boutiqueName=" + boutiqueName + ", operatingCity="
				+ operatingCity + ", professionalCategory=" + professionalCategory + ", yearOfOperation="
				+ yearOfOperation + ", experience=" + experience + ", GSTIN=" + GSTIN + "]";
	}
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	public String getBoutiqueName() {
		return boutiqueName;
	}
	public void setBoutiqueName(String boutiqueName) {
		this.boutiqueName = boutiqueName;
	}
	public String getOperatingCity() {
		return operatingCity;
	}
	public void setOperatingCity(String operatingCity) {
		this.operatingCity = operatingCity;
	}
	public String getProfessionalCategory() {
		return professionalCategory;
	}
	public void setProfessionalCategory(String professionalCategory) {
		this.professionalCategory = professionalCategory;
	}
	public String getYearOfOperation() {
		return yearOfOperation;
	}
	public void setYearOfOperation(String yearOfOperation) {
		this.yearOfOperation = yearOfOperation;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getGSTIN() {
		return GSTIN;
	}
	public void setGSTIN(String gSTIN) {
		GSTIN = gSTIN;
	}
	
	
	
	

}
