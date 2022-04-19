package com.divatt.admin.entity;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Field;

public class AdminModule {
	@Field(name = "mod_name")
	private String modName;
	@Field(name = "mod_privs")
	private ArrayList<String> modPrivs;
	
	

	@Override
	public String toString() {
		return "AdminModule [modName=" + modName + ", modPrivs=" + modPrivs + "]";
	}

	public AdminModule() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AdminModule(String modName, ArrayList<String> modPrivs) {
		super();
		this.modName = modName;
		this.modPrivs = modPrivs;
	}

	public String getModName() {
		return modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public ArrayList<String> getModPrivs() {
		return modPrivs;
	}

	public void setModPrivs(ArrayList<String> modPrivs) {
		this.modPrivs = modPrivs;
	}
	
	
	
}
