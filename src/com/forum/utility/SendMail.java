package com.forum.utility;

import java.util.Date;
import java.util.Properties;

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
//    public static final String PROTOCOL = "smtp";   
//    public static final int PORT = 465;
    public static final String FROM = "singwin@singwin.cn";
    public static final String PWD = "Sing5Win7";
    
    /**
     * ��ȡSession
     * @return
     */
    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);//���÷�������ַ
//        props.put("mail.store.protocol" , PROTOCOL);//����Э��
//        props.put("mail.smtp.port", PORT);//���ö˿�
        props.put("mail.smtp.auth" , true);
         
        Authenticator authenticator = new Authenticator() {
 
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PWD);
            }
             
        };
        Session session = Session.getDefaultInstance(props , authenticator);
         
        return session;
    }
    
    public static void send(String toEmail , String content) {
        Session session = getSession();
        try {
            System.out.println("--send--"+content);
            // Instantiate a message
            Message msg = new MimeMessage(session);
  
            //Set message attributes
            msg.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = {new InternetAddress(toEmail)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("�˺ż����ʼ�");
            msg.setSentDate(new Date());
            msg.setContent(content , "text/html;charset=utf-8");
  
            //Send the message
            Transport.send(msg);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
