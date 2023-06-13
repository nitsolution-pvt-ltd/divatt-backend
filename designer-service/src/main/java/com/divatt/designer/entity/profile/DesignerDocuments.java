package com.divatt.designer.entity.profile;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Field;

public class DesignerDocuments {
	@NotNull(message = "Adhar card is required!")
	@Field(name = "aadhar_card") private String aadharCard;
	
	@NotNull(message = "Pan card is required!")
	@Field(name = "pan_card") private String panCard;
	
	@NotNull (message = "Void check is required!")
	@Field(name = "voidCheck") private String void_check;
	
	@NotNull(message = "Cancel check is required!")
	@Field(name = "cancel_check")
	private String cancelCheck;
	
	@NotNull(message = "gst certificate is required!") 
	@Field(name = "gst_certificate")
	private String gstCertificate;

	public DesignerDocuments() {
		super();
	}

	public DesignerDocuments(@NotNull String aadharCard, @NotNull String panCard, @NotNull String void_check,
			@NotNull String cancelCheck, @NotNull String gstCertificate) {
		super();
		this.aadharCard = aadharCard;
		this.panCard = panCard;
		this.void_check = void_check;
		this.cancelCheck = cancelCheck;
		this.gstCertificate = gstCertificate;
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

	public String getCancelCheck() {
		return cancelCheck;
	}

	public void setCancelCheck(String cancelCheck) {
		this.cancelCheck = cancelCheck;
	}

	public String getGstCertificate() {
		return gstCertificate;
	}

	public void setGstCertificate(String gstCertificate) {
		this.gstCertificate = gstCertificate;
	}

	@Override
	public String toString() {
		return "DesignerDocuments [aadharCard=" + aadharCard + ", panCard=" + panCard + ", void_check=" + void_check
				+ ", cancelCheck=" + cancelCheck + ", gstCertificate=" + gstCertificate + "]";
	}

	
	
}
