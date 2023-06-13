package com.divatt.designer.dto;

import java.util.ArrayList;
import java.util.List;

public class DesignerContactDto {
	List<String>contacts=new ArrayList();

	public DesignerContactDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignerContactDto(List<String> contacts) {
		super();
		this.contacts = contacts;
	}

	public List<String> getContacts() {
		return contacts;
	}

	public void setContacts(List<String> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "DesignerContactDto [contacts=" + contacts + "]";
	}
	

}
