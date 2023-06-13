package com.divatt.admin.DTO;

public class StatusDetails {
	private String reason;
	private String description;
	private String source;
	public StatusDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StatusDetails(String reason, String description, String source) {
		super();
		this.reason = reason;
		this.description = description;
		this.source = source;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Override
	public String toString() {
		return "StatusDetails [reason=" + reason + ", description=" + description + ", source=" + source + "]";
	}
	
	
	

}
