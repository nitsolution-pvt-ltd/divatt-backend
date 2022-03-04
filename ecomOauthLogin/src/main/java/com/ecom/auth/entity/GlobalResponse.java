package com.ecom.auth.entity;

public class GlobalResponse {
	private String resion;
	private String message;
	private int status;

	public GlobalResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GlobalResponse(String resion, String message, int status) {
		super();
		this.resion = resion;
		this.message = message;
		this.status = status;
	}

	@Override
	public String toString() {
		return "GlobalResponse [resion=" + resion + ", message=" + message + ", status=" + status+ "]";
	}

	public String getResion() {
		return resion;
	}

	public void setResion(String resion) {
		this.resion = resion;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
