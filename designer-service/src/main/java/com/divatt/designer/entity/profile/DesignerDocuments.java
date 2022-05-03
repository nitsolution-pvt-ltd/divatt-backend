package com.divatt.designer.entity.profile;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Field;

public class DesignerDocuments {
	@NotNull
	@Field(name = "aadhar_card") private String aadharCard;
	
	@NotNull
	@Field(name = "pan_card") private String panCard;
	
	@NotNull
	@Field(name = "voidCheck") private String void_check;

	public DesignerDocuments() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerDocuments(@NotNull String aadharCard, @NotNull String panCard, @NotNull String void_check) {
		super();
		this.aadharCard = aadharCard;
		this.panCard = panCard;
		this.void_check = void_check;
	}

	@Override
	public String toString() {
		return "DesignerDocuments [aadharCard=" + aadharCard + ", panCard=" + panCard + ", void_check=" + void_check
				+ "]";
	}

	public String getAadharCard() {
		return aadharCard;
	}

	public void setAadharCard(String aadharCard) {
		this.aadharCard = aadharCard;
	}

	public String getPanCard() {
		return panCard;
	}

	public void setPanCard(String panCard) {
		this.panCard = panCard;
	}

	public String getVoid_check() {
		return void_check;
	}

	public void setVoid_check(String void_check) {
		this.void_check = void_check;
	}
	
	
}
