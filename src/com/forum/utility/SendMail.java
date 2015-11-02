package com.forum.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	public static final String HOST = "smtp.ym.163.com";
	public static final String FROM = "singwin@singwin.cn";
	public static final String PWD = "Sing5Win7";

	private static Session getSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.auth", true);

		Authenticator authenticator = new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM, PWD);
			}

		};
		Session session = Session.getDefaultInstance(props, authenticator);

		return session;
	}

	public static void send(String toEmail, String subject, String content) {
		Session session = getSession();
		try {
			System.out.println("------Send------" + content);
			// Instantiate a message
			Message msg = new MimeMessage(session);

			// Set message attributes
			msg.setFrom(new InternetAddress(FROM));

			// List list = new ArrayList();// 不能使用string类型的类型，这样只能发送一个收件人
			// String[] median = toEmail.split(",");// 对输入的多个邮件进行逗号分割
			// for (int i = 0; i < median.length; i++) {
			// list.add(new InternetAddress(median[i]));
			// }
			// InternetAddress[] address = (InternetAddress[]) list.toArray(new
			// InternetAddress[list.size()]);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address[] address = null;
			String[] receivers = toEmail.split(",");
			if (receivers != null) {
				// 为每个邮件接收者创建一个地址
				address = new InternetAddress[receivers.length];
				for (int i = 0; i < receivers.length; i++) {
					address[i] = new InternetAddress(receivers[i]);
				}
			} else {
				address = new InternetAddress[1];
				address[0] = new InternetAddress(receivers[0]);
			}
			msg.setRecipients(Message.RecipientType.TO, address);// 当邮件有多个收件人时，用逗号隔开

			// InternetAddress[] address = { new InternetAddress(toEmail) };
			// msg.setRecipients(Message.RecipientType.TO, address);

			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setContent(content, "text/html;charset=utf-8");

			// Send the message
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
