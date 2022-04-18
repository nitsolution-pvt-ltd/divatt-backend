package com.divatt.auth.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import nonapi.io.github.classgraph.json.Id;

@Document(collection = "tbl_designer_profile")
public class DesignerProfileEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_designer_profile";
	
	@Id
	@Field(name = "_id")
	private Long dId;
	
	@Field(name = "designer_id")
	private Long designerId;
	
	@Field(name = "designer_profile")
	private DesignerProfile designerProfile;
	
	

	public DesignerProfileEntity() {
		super();
		// TODO Auto-generated constructor stub
	}



	public DesignerProfileEntity(Long dId, Long designerId, DesignerProfile designerProfile) {
		super();
		this.dId = dId;
		this.designerId = designerId;
		this.designerProfile = designerProfile;
	}



	@Override
	public String toString() {
		return "DesignerProfileEntity [dId=" + dId + ", designerId=" + designerId + ", designerProfile="
				+ designerProfile + "]";
	}



	public Long getdId() {
		return dId;
	}



	public void setdId(Long dId) {
		this.dId = dId;
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



	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	
	
	
	
	
}
