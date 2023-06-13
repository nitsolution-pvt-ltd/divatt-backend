package com.divatt.admin.DTO;

import java.util.ArrayList;
import java.util.List;

public class ContDTO {
	private String name;
	private String email;
	private Long contact;
	private String type;
	private String reference_id;
	private List<String>notes= new ArrayList<>();
	public ContDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ContDTO(String name, String email, Long contact, String type, String reference_id, List<String> notes) {
		super();
		this.name = name;
		this.email = email;
		this.contact = contact;
		this.type = type;
		this.reference_id = reference_id;
		this.notes = notes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getContact() {
		return contact;
	}
	public void setContact(Long contact) {
		this.contact = contact;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReference_id() {
		return reference_id;
	}
	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}
	public List<String> getNotes() {
		return notes;
	}
	public void setNotes(List<String> notes) {
		this.notes = notes;
	}
	@Override
	public String toString() {
		return "ContDTO [name=" + name + ", email=" + email + ", contact=" + contact + ", type=" + type
				+ ", reference_id=" + reference_id + ", notes=" + notes + "]";
	}
	

}
