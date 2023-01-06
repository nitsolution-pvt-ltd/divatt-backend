package com.divatt.admin.entity;

import java.util.HashMap;

import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.BasicDBList;

public class AdminModule {
	@Field(name = "mod_name")
	private String modName;
	@Field(name = "mod_privs")
	private HashMap<String,Boolean> modPrivs;
	
	private Object modDetails;
	
	public AdminModule(String modName, HashMap<String, Boolean> modPrivs, BasicDBList modDetails) {
		super();
		this.modName = modName;
		this.modPrivs = modPrivs;
		this.modDetails = modDetails;
	}





	public AdminModule() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	

	@Override
	public String toString() {
		return "AdminModule [modName=" + modName + ", modPrivs=" + modPrivs + "]";
	}



	public String getModName() {
		return modName;
	}



	public void setModName(String modName) {
		this.modName = modName;
	}



	public Object getModDetails() {
		return modDetails;
	}





	public void setModDetails(Object modDetails) {
		this.modDetails = modDetails;
	}





	public HashMap<String, Boolean> getModPrivs() {
		return modPrivs;
	}



	public void setModPrivs(HashMap<String, Boolean> modPrivs) {
		this.modPrivs = modPrivs;
	}



	


	
	
	
}
