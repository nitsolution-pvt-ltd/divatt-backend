package com.divatt.admin.entity;

import java.io.File;

public class SendMail {

	String senderMailId;
	String subject;
	String body;
	boolean enableHtml;
	File file;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public SendMail(String senderMailId, String subject, String body, boolean enableHtml, File file) {
		super();
		this.senderMailId = senderMailId;
		this.subject = subject;
		this.body = body;
		this.enableHtml = enableHtml;
		this.file = file;
	}
	public String getSenderMailId() {
		return senderMailId;
	}
	public void setSenderMailId(String senderMailId) {
		this.senderMailId = senderMailId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public boolean isEnableHtml() {
		return enableHtml;
	}
	public void setEnableHtml(boolean enableHtml) {
		this.enableHtml = enableHtml;
	}
	@Override
	public String toString() {
		return "SendMail [senderMailId=" + senderMailId + ", subject=" + subject + ", body=" + body + ", enableHtml="
				+ enableHtml + ", file=" + file + "]";
	}
	public SendMail(String senderMailId, String subject, String body, boolean enableHtml) {
		super();
		this.senderMailId = senderMailId;
		this.subject = subject;
		this.body = body;
		this.enableHtml = enableHtml;
	}
	public SendMail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
