package com.divatt.admin.exception;

public class CustomErrorMessage {

	private int statuss;
	private String messagee;
	private long timeStampp;
	public int getStatuss() {
		return statuss;
	}
	public void setStatuss(int statuss) {
		this.statuss = statuss;
	}
	public String getMessagee() {
		return messagee;
	}
	public void setMessagee(String messagee) {
		this.messagee = messagee;
	}
	public long getTimeStampp() {
		return timeStampp;
	}
	public void setTimeStampp(long timeStampp) {
		this.timeStampp = timeStampp;
	}
	@Override
	public String toString() {
		return "CustomeErrorMessage [statuss=" + statuss + ", messagee=" + messagee + ", timeStampp=" + timeStampp
				+ "]";
	}
	public CustomErrorMessage(int statuss, String messagee, long timeStampp) {
		super();
		this.statuss = statuss;
		this.messagee = messagee;
		this.timeStampp = timeStampp;
	}
	public CustomErrorMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
