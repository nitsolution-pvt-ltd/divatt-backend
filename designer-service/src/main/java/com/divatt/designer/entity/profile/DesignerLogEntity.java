package com.divatt.designer.entity.profile;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import nonapi.io.github.classgraph.json.Id;

@Document(collection = "tbl_designer_log")
public class DesignerLogEntity {
	
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
	

}
