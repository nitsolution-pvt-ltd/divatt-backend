package com.divatt.designer.helper;

import java.io.File;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.divatt.designer.entity.SendMail;

public class EmailSenderThread  extends Thread{

	String senderMailId;
	String subject;
	String body;
	boolean enableHtml;
	File file;
	public EmailSenderThread(String senderMailId, String subject, String body, boolean enableHtml, File file) {
		super();
		this.senderMailId = senderMailId;
		this.subject = subject;
		this.body = body;
		this.enableHtml = enableHtml;
		this.file = file;
	}
	@Override
	public void run() {
		SendMail sendMail= new SendMail();
		sendMail.setBody(body);
		sendMail.setEnableHtml(enableHtml);
		sendMail.setFile(null);
		sendMail.setSenderMailId(senderMailId);
		sendMail.setSubject(subject);
		RestTemplate mailLink= new RestTemplate();
		ResponseEntity<String> mailStatus=mailLink.postForEntity("https://65.1.190.195:8080/dev/auth/sendMail", sendMail, String.class);
		System.out.println(mailStatus.getBody());
	}
	public EmailSenderThread()
	{
		EmailSenderThread thread= new EmailSenderThread();
		thread.start();
	}
}
