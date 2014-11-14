package cguide.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by IntelliJ IDEA.
 * User: andre
 * Date: 12/1/12
 * Time: 2:21 PM
 */
public class Mailer {

    public static void sendActivationMail(String email, String key, String username,String password)
    throws MessagingException, NamingException , UnsupportedEncodingException{
        Properties props = new Properties();
        InitialContext ictx = new InitialContext(props);
        Session mailSession = (Session) ictx.lookup("java:jboss/mail/Gmail");
        Message message = MailUtils.createActivationEmail(mailSession, key, email, username, password );
        MailExecutor.getInstance().addMailJob(message, mailSession);
    }


    public static void sendEmailChangedMail(String email, String key, String username)
    throws MessagingException, NamingException , UnsupportedEncodingException{
        Properties props = new Properties();
        InitialContext ictx = new InitialContext(props);
        Session mailSession = (Session) ictx.lookup("java:jboss/mail/Gmail");
        Message message = MailUtils.createPasswordChangedEmail(mailSession, key, email, username );
        MailExecutor.getInstance().addMailJob(message, mailSession);
    }

    public static void sendPasswordRecoveryEmail(String email, String password, String username)
    throws MessagingException, NamingException, UnsupportedEncodingException{
        Properties props = new Properties();
        InitialContext ictx = new InitialContext(props);
        Session mailSession = (Session) ictx.lookup("java:jboss/mail/Gmail");
        Message message = MailUtils.createRecoveryEmail(mailSession, email, password, username);
        MailExecutor.getInstance().addMailJob(message, mailSession);
    }
}
