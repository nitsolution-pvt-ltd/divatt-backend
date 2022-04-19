package com.divatt.admin.entity;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.data.mongodb.core.mapping.Field;

public class AdminModule {
	@Field(name = "mod_name")
	private String modName;
	@Field(name = "mod_privs")
	private ArrayList<String> modPrivs;
	
	private HashMap<String,Boolean> modPrivsDB;

	

	public AdminModule(String modName, ArrayList<String> modPrivs, HashMap<String, Boolean> modPrivsDB) {
		super();
		this.modName = modName;
		this.modPrivs = modPrivs;
		this.modPrivsDB = modPrivsDB;
	}

	public AdminModule() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public String toString() {
		return "AdminModule [modName=" + modName + ", modPrivs=" + modPrivs + ", modPrivsDB=" + modPrivsDB + "]";
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

	public HashMap<String, Boolean> getModPrivsDB() {
		return modPrivsDB;
	}

	public void setModPrivsDB(HashMap<String, Boolean> modPrivsDB) {
		this.modPrivsDB = modPrivsDB;
	}


	
	
	
}
