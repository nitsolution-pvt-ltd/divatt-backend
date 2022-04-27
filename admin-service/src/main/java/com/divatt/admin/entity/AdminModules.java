package com.divatt.admin.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.BasicDBList;

import springfox.documentation.spring.web.json.Json;

@Document(collection = "tbl_admin_mdata")
public class AdminModules {
	
	@Transient
	public static final String SEQUENCE_NAME = "tbl_admin_mdata";
	
	@Id
	@Field(name = "_id")
	private Long id;
	
	@Field(name = "meta_key")
	private String metaKey;
	
	@Field(name = "admin_modules")
	private BasicDBList adminModules;
	
	@Field(name = "role_name")
	private String roleName;
	
	@Field(name = "modules")
	private ArrayList<AdminModule> modules;
	
	@Field(name = "is_deleted")
	private Boolean isDeleted;
	
	public AdminModules() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminModules(Long id, String metaKey, BasicDBList adminModules, String roleName,
			ArrayList<AdminModule> modules, Boolean isDeleted) {
		super();
		this.id = id;
		this.metaKey = metaKey;
		this.adminModules = adminModules;
		this.roleName = roleName;
		this.modules = modules;
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "AdminModules [id=" + id + ", metaKey=" + metaKey + ", adminModules=" + adminModules + ", roleName="
				+ roleName + ", modBasicDBListules=" + modules + ", isDeleted=" + isDeleted + "]";
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMetaKey() {
		return metaKey;
	}
	public void setMetaKey(String metaKey) {
		this.metaKey = metaKey;
	}
	public BasicDBList getAdminModules() {
		return adminModules;
	}
	public void setAdminModules(BasicDBList adminModules) {
		this.adminModules = adminModules;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public ArrayList<AdminModule> getModules() {
		return modules;
	}
	public void setModules(ArrayList<AdminModule> modules) {
		this.modules = modules;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

}
