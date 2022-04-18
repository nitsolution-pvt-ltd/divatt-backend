package com.divatt.auth.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import nonapi.io.github.classgraph.json.Id;
import springfox.documentation.spring.web.json.Json;

@Document(collection = "tbl_designer_profile")
public class DesignerProfile {
	
	@Id
	private Long id;
	
	@Field(name = "designer_id")
	private Long designerId;
	@Field(name = "designer_profile")
	private Json designerProfile;
	
	
	public DesignerProfile() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DesignerProfile(Long id, Long designerId, Json designerProfile) {
		super();
		this.id = id;
		this.designerId = designerId;
		this.designerProfile = designerProfile;
	}


	@Override
	public String toString() {
		return "DesignerProfile [id=" + id + ", designerId=" + designerId + ", designerProfile=" + designerProfile
				+ "]";
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


	public Json getDesignerProfile() {
		return designerProfile;
	}


	public void setDesignerProfile(Json designerProfile) {
		this.designerProfile = designerProfile;
	}
	
	
	
	
}
