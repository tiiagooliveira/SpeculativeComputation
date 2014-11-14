package cguide.utils;

import java.io.UnsupportedEncodingException;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;

public class MailUtils {

    public static Message createActivationEmail(Session mailSession, String key, String email, String username, String password)
            throws MessagingException, NamingException, UnsupportedEncodingException {
        MimeMessage mail = new MimeMessage(mailSession);
        Address from = new InternetAddress("noreply@cguide.com","CompGuide");
        mail.setFrom(from);
        mail.setSubject("CGuide - Account Activation");
        mail.setRecipients(Message.RecipientType.TO,
                javax.mail.internet.InternetAddress.parse(email, false));
        mail.setText("Hi " + username + "! Welcome to cguide.\n" +
                "You or someone else has used your email account ("+email+") to register an account at cguide Portal.\n" +
                "The acount info is:\n Username = "+username+"\n Password = "+password+
                "To finish the registration process you should visit the following link in the next 24 hours to" +
                " activate your user account, otherwise the information will be automaticaly deleted by the system " +
                "and you should apply again:\n\n" +
                " http://localhost:8080/CGuide_war_exploded/activation?username="+username+"&key="+ key);

        return mail;
    }

    public static Message createPasswordChangedEmail(Session mailSession, String key, String email, String username)
            throws MessagingException, NamingException, UnsupportedEncodingException {
        MimeMessage mail = new MimeMessage(mailSession);
        Address from = new InternetAddress("noreply@cguide.com", "cguide");
        mail.setFrom(from);
        mail.setSubject("cguide - Email changed");
        mail.setRecipients(Message.RecipientType.TO,
                javax.mail.internet.InternetAddress.parse(email, false));
        mail.setText("Hi!\n" +
                "You changed your email at cguide, and you need to confirm it, in order to login.\n" +
                "Please folow the following link to confirm your email:\n\n" +
                " http://localhost:8080/CGuide_war_exploded/activation?username="+username+"&key="+ key);

        return mail;
    }

    public static Message createRecoveryEmail(Session mailSession, String email, String password, String username)
            throws MessagingException, NamingException, UnsupportedEncodingException {
        MimeMessage mail = new MimeMessage(mailSession);
        mail.setSubject("CGuide - Password");
        Address from = new InternetAddress("noreply@cguide.com","CGuide password system");
        mail.setFrom(from);
        mail.setRecipients(Message.RecipientType.TO,
                javax.mail.internet.InternetAddress.parse(email, false));
        mail.setText("Hi " + username + "!\n" +
                "You or someone else has used your email account (" + email + ") to request a new password for your account.\n\n" +
                "Your previous password is no longer valid. Use this password to login from now on:\n" +
                "\nPassword: " + password + "\n\n cguide Team.");
        return mail;
    }
}
