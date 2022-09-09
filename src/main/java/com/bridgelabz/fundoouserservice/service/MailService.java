package com.bridgelabz.fundoouserservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/*
 * Purpose : MailService used to Send a mail
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@Component
@Slf4j
public class MailService {
    /*
     * Purpose : Implement the Logic of Send a Mail to User
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and bankDetailsDTO
     * */
    public static void send(String toEmail, String body, String subject) {
        final String fromEmail = System.getenv("Email");
        final String password = System.getenv("Password");
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(properties, authenticator);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/HTML;charset =UTF-8");
            message.setHeader("format", "flowed");
            message.setHeader("Content-Transfer-Encoding", "8bit");
            message.setFrom(new InternetAddress("no_replay@gmail.com", "NoReply"));
            message.setReplyTo(InternetAddress.parse(System.getenv("Email"), false));
            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");
            message.setSentDate(new Date());
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(message);
            log.info("Email Sent Successfully.........");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}