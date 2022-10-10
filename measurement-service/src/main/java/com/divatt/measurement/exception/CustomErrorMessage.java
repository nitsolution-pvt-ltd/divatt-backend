package com.divatt.measurement.exception;

public class CustomErrorMessage {
	private int status;
	private String message;
	private long timeStamp;
	public int getStatus() {
		return status;
	}
	public void setStatuss(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	@Override
	public String toString() {
		return "CustomeErrorMessage [status=" + status + ", message=" + message + ", timeStamp=" + timeStamp
				+ "]";
	}
	public CustomErrorMessage(int status, String message, long timeStamp) {
		super();
		this.status = status;
		this.message = message;
		this.timeStamp = timeStamp;
	}
	public CustomErrorMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
}
