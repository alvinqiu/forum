package com.forum.utility;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailSender {
	Logger logger = Logger.getLogger(EmailSender.class);

	public JavaMailSenderImpl configur(String host, String username,
			String password) {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost(host);
		javaMailSenderImpl.setUsername(username);
		javaMailSenderImpl.setPassword(password);
		Properties pros = new Properties();
		pros.put(" mail.smtp.auth ", " true ");
		pros.put(" mail.smtp.timeout ", " 25000 ");
		javaMailSenderImpl.setJavaMailProperties(pros);
		logger.debug("host:" + host + "username:" + username);
		return javaMailSenderImpl;
	}

	public void sendEmail(String from, String subject, String[] recievces,
			String text, JavaMailSenderImpl javaMailSenderImpl) {

		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(from);
			simpleMailMessage.setSubject(subject);
			simpleMailMessage.setTo(recievces);
			simpleMailMessage.setText(text);
			javaMailSenderImpl.send(simpleMailMessage);
		} catch (MailException e) {
			logger.debug("connetion issue:" + e.getMessage());
		}
	}
}
