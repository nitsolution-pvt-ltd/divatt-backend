package com.divatt.admin.DTO;

import java.util.List;

public class RazorpayXPaymentDTO {
	private String entity;
	private String account_id;
	private String event;
	private List<String> contains;
	private Payload payload;
	private Double created_at;
	public RazorpayXPaymentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RazorpayXPaymentDTO(String entity, String account_id, String event, List<String> contains, Payload payload,
			Double created_at) {
		super();
		this.entity = entity;
		this.account_id = account_id;
		this.event = event;
		this.contains = contains;
		this.payload = payload;
		this.created_at = created_at;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public List<String> getContains() {
		return contains;
	}
	public void setContains(List<String> contains) {
		this.contains = contains;
	}
	public Payload getPayload() {
		return payload;
	}
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	public Double getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Double created_at) {
		this.created_at = created_at;
	}
	@Override
	public String toString() {
		return "RazorpayXPaymentDTO [entity=" + entity + ", account_id=" + account_id + ", event=" + event
				+ ", contains=" + contains + ", payload=" + payload + ", created_at=" + created_at + "]";
	}
	
	

}
