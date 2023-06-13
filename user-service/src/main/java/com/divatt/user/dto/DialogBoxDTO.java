package com.divatt.user.dto;

public class DialogBoxDTO {
	private String message;
	private String date;
	public DialogBoxDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DialogBoxDTO(String message, String date) {
		super();
		this.message = message;
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "DialogBoxDTO [message=" + message + ", date=" + date + "]";
	}
	

}
