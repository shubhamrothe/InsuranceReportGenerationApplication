 package com.reports.utils;

import java.io.File;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	private JavaMailSender mailSender; //It is predefined interface provided by spring mail dependency
	
	public EmailUtils(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public boolean sendEmail(String subject, String body, String to, File f) {
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.setTo(to);
			helper.addAttachment("Plans-Info", f);
			mailSender.send(mimeMessage);
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return true;
	}
	
}

