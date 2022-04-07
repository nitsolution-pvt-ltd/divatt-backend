package com.divatt.admin.entity;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import springfox.documentation.spring.web.json.Json;

@Document(collection = "tbl_admin_mdata")
public class AdminModules {
	
	@Id
	private Object mId;
	
	@Field(name = "meta_key")
	private String metaKey;
	
	@Field(name = "admin_modules")
	private ArrayList<Object> adminModules;
	
	@Field(name = "role_name")
	private String roleName;
	
	@Field(name = "modules")
	private ArrayList<Object> modules;

	public AdminModules() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public AdminModules(Object mId, String metaKey, ArrayList<Object> adminModules, String roleName,
			ArrayList<Object> modules) {
		super();
		this.mId = mId;
		this.metaKey = metaKey;
		this.adminModules = adminModules;
		this.roleName = roleName;
		this.modules = modules;
	}



	

	public Object getmId() {
		return mId;
	}

	public String getRoleName() {
		return roleName;
	}



	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



	public ArrayList<Object> getModules() {
		return modules;
	}



	public void setModules(ArrayList<Object> modules) {
		this.modules = modules;
	}



	@Override
	public String toString() {
		return "AdminModules [mId=" + mId + ", metaKey=" + metaKey + ", adminModules=" + adminModules + ", roleName="
				+ roleName + ", modules=" + modules + "]";
	}



	public void setmId(Object mId) {
		this.mId = mId;
	}

	public String getMetaKey() {
		return metaKey;
	}

	public void setMetaKey(String metaKey) {
		this.metaKey = metaKey;
	}

	public ArrayList<Object> getAdminModules() {
		return adminModules;
	}

	public void setAdminModules(ArrayList<Object> adminModules) {
		this.adminModules = adminModules;
	}
	
	

}
