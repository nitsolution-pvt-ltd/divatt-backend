package com.divatt.auth.services;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.divatt.auth.exception.CustomException;

@Service
public class MailService {
	@Autowired
	JavaMailSender mailSender;

//	public void sendEmail(String to, String subject, String body,Boolean enableHtml) {
//
//		try {
//
//			MimeMessage message = mailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(message);
//
//			helper.setSubject(subject);
//			helper.setFrom("no-reply@nitsolution.in");
//			helper.setTo(to);
//			helper.setText(body, enableHtml);
////			helper.addAttachment(body, null);
//			mailSender.send(message);
//		} catch (Exception e) {
//			throw new CustomException(e.getMessage());
//		}
//
//	}
	public void sendEmail(String to, String subject, String body,Boolean enableHtml) {

		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setSubject(subject);
			helper.setFrom("no-reply@nitsolution.in");
			helper.setTo(to);
			helper.setText(body, enableHtml);
//			helper.addAttachment(body, null);
			mailSender.send(message);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	public void sendEmailWithAttachment(String to, String subject, String body,Boolean enableHtml,File file) {

		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setSubject(subject);
			helper.setFrom("no-reply@nitsolution.in");
			helper.setTo(to);
			helper.setText(body, enableHtml);
			helper.addAttachment("Invoice", file);
			mailSender.send(message);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
}
