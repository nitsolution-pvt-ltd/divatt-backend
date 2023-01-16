package com.divatt.user.dto;

import org.json.simple.JSONObject;


public class DesignerRequestDTO {

	private Long designerId;
	

	private JSONObject designerProfile;


	public DesignerRequestDTO() {
		super();
	}


	public DesignerRequestDTO(Long designerId, JSONObject designerProfile) {
		super();
		this.designerId = designerId;
		this.designerProfile = designerProfile;
	}


	@Override
	public String toString() {
		return "DesignerRequestDTO [designerId=" + designerId + ", designerProfile=" + designerProfile + "]";
	}


	public Long getDesignerId() {
		return designerId;
	}


	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
	}


	public JSONObject getDesignerProfile() {
		return designerProfile;
	}


	public void setDesignerProfile(JSONObject designerProfile) {
		this.designerProfile = designerProfile;
	}
	
}
