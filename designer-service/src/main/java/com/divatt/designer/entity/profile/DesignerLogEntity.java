package com.divatt.designer.entity.profile;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import nonapi.io.github.classgraph.json.Id;
import springfox.documentation.spring.web.json.Json;

@Document(collection = "tbl_designer_log")
public class DesignerLogEntity {
	
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_designer_log";
	
	@Id
	private Long id;
	
	@Field(name = "designer_id")
	private Long designerId;
	
	@Field(name = "log_type")
	private String logType;
	
	@Field(name = "is_active")
	private Boolean isActive;
	
	@Field(name = "_comment")
	private String comment;
	
	@Field(name = "updated_by")
	private String updatedBy;
	
	@Field(name = "updated_on")
	private String updatedOn;
	
	@Field(name = "requested_by")
	private String requestedBy;
	
	@Field(name = "requested_on")
	private String requestedOn;
	
	@Field(name = "status")
	private String status;
	
	@Field(name = "before")
	private Json before;
	
	@Field(name = "after")
	private Json after;

	public DesignerLogEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerLogEntity(Long id, Long designerId, String logType, Boolean isActive, String comment,
			String updatedBy, String updatedOn, String requestedBy, String requestedOn, String status, Json before,
			Json after) {
		super();
		this.id = id;
		this.designerId = designerId;
		this.logType = logType;
		this.isActive = isActive;
		this.comment = comment;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.requestedBy = requestedBy;
		this.requestedOn = requestedOn;
		this.status = status;
		this.before = before;
		this.after = after;
	}

	@Override
	public String toString() {
		return "DesignerLogEntity [id=" + id + ", designerId=" + designerId + ", logType=" + logType + ", isActive="
				+ isActive + ", comment=" + comment + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn
				+ ", requestedBy=" + requestedBy + ", requestedOn=" + requestedOn + ", status=" + status + ", before="
				+ before + ", after=" + after + "]";
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

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getRequestedOn() {
		return requestedOn;
	}

	public void setRequestedOn(String requestedOn) {
		this.requestedOn = requestedOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Json getBefore() {
		return before;
	}

	public void setBefore(Json before) {
		this.before = before;
	}

	public Json getAfter() {
		return after;
	}

	public void setAfter(Json after) {
		this.after = after;
	}
	
	
	
	
	
	

}
