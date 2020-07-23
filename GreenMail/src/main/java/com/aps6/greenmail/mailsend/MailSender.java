package com.aps6.greenmail.mailsend;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {
    public static void main(String[] args) {

        String userName = "ruiranoliveirasantos@gmail.com";
        String password = "";
        String[] recipients = {"ruiran_oliveira2009@hotmail.com"};
        String subject = "Teste";
        String content = "<p>Teste APS</p>";


        try {
            sendMail(userName, password, recipients, subject, content);
        } catch (MessagingException exception) {
            throw new RuntimeException(exception);
        }

    }

    public static void sendMail(String userName, String password, String[] messageRecipients,
                                String messageSubject, String messageContent)
            throws MessagingException {

        Message message = new MimeMessage(createSession(userName, password));
        message.setFrom(new InternetAddress("ruiranoliveirasantos@gmail.com"));

        Address[] toUser = {};

        for (String recipient : messageRecipients) {
            toUser = InternetAddress.parse(recipient);
        }

        message.setRecipients(Message.RecipientType.TO, toUser);
        message.setSubject(messageSubject);
        message.setContent(messageContent, "text/html");

        Transport.send(message);
    }

    public static Session createSession(String userName, String password) {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", 465);
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.port", 465);


        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        session.setDebug(true);

        return session;
    }
}