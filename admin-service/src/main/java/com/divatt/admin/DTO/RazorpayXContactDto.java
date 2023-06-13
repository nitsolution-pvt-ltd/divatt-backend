package com.divatt.admin.DTO;

import java.util.List;

public class RazorpayXContactDto {
	private String id;
	private String Entity;
	private String name;
	private String email;
	private Long contact;
	private String type;
	private String referanceId;
	private String batch_id;
	private String active;
	private List<String>notes;
	private long created_at;
	public RazorpayXContactDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RazorpayXContactDto(String id, String entity, String name, String email, Long contact, String type,
			String referanceId, String batch_id, String active, List<String> notes, long created_at) {
		super();
		this.id = id;
		Entity = entity;
		this.name = name;
		this.email = email;
		this.contact = contact;
		this.type = type;
		this.referanceId = referanceId;
		this.batch_id = batch_id;
		this.active = active;
		this.notes = notes;
		this.created_at = created_at;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEntity() {
		return Entity;
	}
	public void setEntity(String entity) {
		Entity = entity;
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
	public String getReferanceId() {
		return referanceId;
	}
	public void setReferanceId(String referanceId) {
		this.referanceId = referanceId;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public List<String> getNotes() {
		return notes;
	}
	public void setNotes(List<String> notes) {
		this.notes = notes;
	}
	public long getCreated_at() {
		return created_at;
	}
	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}
	@Override
	public String toString() {
		return "RazorpayXContactDto [id=" + id + ", Entity=" + Entity + ", name=" + name + ", email=" + email
				+ ", contact=" + contact + ", type=" + type + ", referanceId=" + referanceId + ", batch_id=" + batch_id
				+ ", active=" + active + ", notes=" + notes + ", created_at=" + created_at + "]";
	}


}
