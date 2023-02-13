package com.divatt.admin.helper;

import java.io.File;

import org.springframework.web.client.RestTemplate;

import com.divatt.admin.entity.SendMail;

public class EmailSenderThread extends Thread {

	String AUTH_SERVICE;
	RestTemplate mailLink;
	String senderMailId;
	String subject;
	String body;
	boolean enableHtml;
	File file;
	

	public EmailSenderThread(String senderMailId, String subject, String body, boolean enableHtml, File file,
			RestTemplate mailLink) {
		super();
		this.AUTH_SERVICE=AUTH_SERVICE;
		this.senderMailId = senderMailId;
		this.subject = subject;
		this.body = body;
		this.enableHtml = enableHtml;
		this.file = file;
		this.mailLink = mailLink;
	}

	@Override
	public void run() {
		SendMail sendMail = new SendMail();
		sendMail.setBody(body);
		sendMail.setEnableHtml(enableHtml);
		sendMail.setFile(null);
		sendMail.setSenderMailId(senderMailId);
		sendMail.setSubject(subject);
		mailLink.postForEntity(AUTH_SERVICE+"auth/sendMail", sendMail,String.class);
	}

	public EmailSenderThread() {
		EmailSenderThread thread = new EmailSenderThread();
		thread.start();
	}
}
