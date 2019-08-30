package camagru;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    public static void sendMail(EmailContent content) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(content.getSender(), content.getPassword());
            }
        });
        Message message = prepareMessage(session, content);
        try {
            Transport.send(message);
            System.out.println("Check your email");
        } catch (MessagingException e) {
            System.out.println(":(");
            e.printStackTrace();
        }
    }

    private static Message prepareMessage(Session session, EmailContent content) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(content.getSender()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(content.getRecipient()));
            message.setSubject(content.getSubject());
            message.setText(content.getBody());
            return (message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return (null);
    }
}
