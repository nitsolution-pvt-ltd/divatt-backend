package com.divatt.designer.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Field;



public class DesignerContactEntity {
	
	
	
	@NotNull
	@Field(name = "email")
	private String email;
	
	
	private String Name;
	
	@NotNull
	@Field(name = "mobileNo")
	private String contact;

	public DesignerContactEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerContactEntity(@NotNull String email, String name, @NotNull String contact) {
		super();
		this.email = email;
		Name = name;
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "DesignerContactEntity [email=" + email + ", Name=" + Name + ", contact=" + contact + "]";
	}

	
	

	
}
