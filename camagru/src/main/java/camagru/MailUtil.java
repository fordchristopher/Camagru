package camagru;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.regex.Pattern;

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
        if (message == null)
            return ;
        try {
            Transport.send(message);
        } catch (MessagingException e) {
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

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
