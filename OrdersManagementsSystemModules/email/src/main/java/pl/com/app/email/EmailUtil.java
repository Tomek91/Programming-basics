package pl.com.app.email;


import pl.com.app.exceptions.MyException;
import pl.com.app.parsers.json.FileNames;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.util.Properties;

public class EmailUtil {

    public static boolean send(String emailTo, String subject, String htmlText) {
        try {
            if (emailTo == null || subject == null || htmlText == null) {
                throw new NullPointerException("ITEMS ARE NULL");
            }
            Properties props = new Properties();
            props.load(new FileInputStream(FileNames.MAIL_PROP));

            final String username = props.getProperty("mail.smtp.user");
            final String password = props.getProperty("mail.smtp.password");
            final String emailFrom = props.getProperty("mail.smtp.from");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(subject);
            message.setText(htmlText);

            Transport.send(message);

            System.out.println("Mail wysłano pomyślnie.");
        } catch (Exception e) {
            throw new MyException("SEND EMAIL EXCEPTION");
        }
        return true;
    }
}
