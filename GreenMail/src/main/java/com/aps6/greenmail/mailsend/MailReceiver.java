package com.aps6.greenmail.mailsend;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class MailReceiver {
    public static void main(String[] args) {
        String userName = "ruiranoliveirasantos@gmail.com";
        String password = "";

        check(userName, password);
    }

    public static void check(String userName, String password) {
        try {
            Properties properties = new Properties();

            properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.pop3.socketFactory.fallback", "false");
            properties.put("mail.pop3.socketFactory.port", "995");
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.host", "pop.gmail.com");
            properties.put("mail.pop3.user", userName);
            properties.put("mail.store.protocol", "pop3");

            Authenticator authenticator = authenticate(userName, password);

            Session emailSession = Session.getDefaultInstance(properties, authenticator);

            Store store = emailSession.getStore("pop3");
            store.connect("pop.gmail.com", userName, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();

            for (Message message : messages) {

                System.out.println("---------------------------------------------");
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + Arrays.toString(message.getFrom()));
                System.out.println("Text: " + toString(message));

            }

            emailFolder.close(false);
            store.close();

        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Authenticator authenticate(String userName, String password) {
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        return authenticator;
    }

    public static String toString(Message message) throws IOException, MessagingException {
        Object messageContent = message.getContent();

        if (messageContent instanceof MimeMultipart) {
            MimeMultipart multipart = (MimeMultipart) messageContent;
            if (multipart.getCount() > 0) {
                BodyPart part = multipart.getBodyPart(0);
                messageContent = part.getContent();
            }
        }

        if (messageContent != null)
            return messageContent.toString();

        return null;
    }

}
