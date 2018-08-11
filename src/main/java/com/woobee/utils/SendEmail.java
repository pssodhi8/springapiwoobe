package com.woobee.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendEmail {
	private static final Logger logger = LoggerFactory.getLogger(SendEmail.class);
	public static boolean sendTo(HashMap<String, String> details){
		boolean status = false;
	      String to = details.get("emailid");
	      
	      String from = "support@woobeworld.com";
	      final String username = "lalgpannu@gmail.com";
	      //final String password = "cvkkwnpwcfbsxtzy";
	      final String password = "vehpwwfymynadplw";
	      
	      Properties props = System.getProperties();
	      props.setProperty("mail.smtp.host", "smtp.gmail.com");
	      props.setProperty("mail.user", username);
	      props.setProperty("mail.password", password);
	      //for G-mail
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.port", "587");
	      Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	          protected PasswordAuthentication getPasswordAuthentication() {
	              return new PasswordAuthentication(username, password);
	           }
	        });
	      try {
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from,"Woobe Support"));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         if(details.get("type")== "resetPass"){
	        	 message.setSubject("New Password");
		         message.setContent("<p>Dear User </p>"
		         		+ "<p style=\"margin-left:80px; margin-top:20px;\">Your new password is " + details.get("newpass") +"</p>"
		         		+ "<p>Regards<br/>Team Woobe</p>", "text/html");
	         }
	         else if(details.get("type")== "sendOTP"){
	        	 message.setSubject("OTP for Signup");
		         message.setContent("<p>Dear User </p>"
		         		+ "<p style=\"margin-left:80px; margin-top:20px;\">You or somebody tried to signup using this Email-Id. OTP for signup is " + details.get("otp") +"</p>"
		         		+ "<p>Regards<br/>Team Woobe</p>", "text/html");
	         }
	         Transport.send(message);
	         logger.info("Message sent successfully....");
	         status = true;
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return status;
	}

}
