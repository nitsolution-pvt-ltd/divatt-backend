package com.divatt.designer.entity.profile;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import nonapi.io.github.classgraph.json.Id;

@Document(collection = "tbl_designer_profile")
public class DesignerProfileEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_designer_profile";
	
	@Id
	private Long id;
	
	@Field(name = "designer_id")
	private Long designerId;
	
	@NotNull
	@Field(name = "designer_profile")
	private DesignerProfile designerProfile;
	
	@NotNull
	@Field(name = "boutique_profile")
	private BoutiqueProfile boutiqueProfile;
	
	@Field(name = "social_profile")
	private SocialProfile socialProfile;
	
	private String designerName;
	
	private String profileStatus;
	
	private String accountStatus;
	
	private DesignerPersonalInfoEntity designerPersonalInfoEntity;

	public DesignerProfileEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	



	



	public DesignerProfileEntity(Long id, Long designerId, @NotNull DesignerProfile designerProfile,
			@NotNull BoutiqueProfile boutiqueProfile, SocialProfile socialProfile, String designerName,
			String profileStatus, String accountStatus, DesignerPersonalInfoEntity designerPersonalInfoEntity) {
		super();
		this.id = id;
		this.designerId = designerId;
		this.designerProfile = designerProfile;
		this.boutiqueProfile = boutiqueProfile;
		this.socialProfile = socialProfile;
		this.designerName = designerName;
		this.profileStatus = profileStatus;
		this.accountStatus = accountStatus;
		this.designerPersonalInfoEntity = designerPersonalInfoEntity;
	}











	@Override
	public String toString() {
		return "DesignerProfileEntity [id=" + id + ", designerId=" + designerId + ", designerProfile=" + designerProfile
				+ ", boutiqueProfile=" + boutiqueProfile + ", socialProfile=" + socialProfile + ", designerName="
				+ designerName + ", profileStatus=" + profileStatus + ", accountStatus=" + accountStatus
				+ ", designerPersonalInfoEntity=" + designerPersonalInfoEntity + "]";
	}



	public DesignerPersonalInfoEntity getDesignerPersonalInfoEntity() {
		return designerPersonalInfoEntity;
	}



	public String getProfileStatus() {
		return profileStatus;
	}



	public void setProfileStatus(String profileStatus) {
		this.profileStatus = profileStatus;
	}



	public String getAccountStatus() {
		return accountStatus;
	}



	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}



	public void setDesignerPersonalInfoEntity(DesignerPersonalInfoEntity designerPersonalInfoEntity) {
		this.designerPersonalInfoEntity = designerPersonalInfoEntity;
	}



	public String getDesignerName() {
		return designerName;
	}



	public void setDesignerName(String designerName) {
		this.designerName = designerName;
	}



	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}


	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getDesignerId() {
		return designerId;
	}

	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
	}

	public DesignerProfile getDesignerProfile() {
		return designerProfile;
	}

	public void setDesignerProfile(DesignerProfile designerProfile) {
		this.designerProfile = designerProfile;
	}

	public BoutiqueProfile getBoutiqueProfile() {
		return boutiqueProfile;
	}

	public void setBoutiqueProfile(BoutiqueProfile boutiqueProfile) {
		this.boutiqueProfile = boutiqueProfile;
	}

	public SocialProfile getSocialProfile() {
		return socialProfile;
	}

	public void setSocialProfile(SocialProfile socialProfile) {
		this.socialProfile = socialProfile;
	}
	

}
