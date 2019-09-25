package source;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


public class EmailSource {

    private static final String RED_COLOR = "\u001B[31m";
    private static String host = "smtp.gmail.com";
    private static String user = "...";
    private static String pass = "...";

    public static void sendEmail(String toAddr, String subject, String body) {
        System.out.println("The email is preparing for sending. Please wait...");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, null);

        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddr));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message, user, pass);
            System.out.println("The email with your data sent successfully.");
        } catch (MessagingException e) {
            System.out.println(RED_COLOR+"Failed to send email: " + e.getMessage());
        }

    }
}
