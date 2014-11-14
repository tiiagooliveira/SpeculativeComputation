package cguide.utils;

import java.security.MessageDigest;

import org.jboss.resteasy.util.Base64;

/**
 * Created by IntelliJ IDEA.
 * User: andre
 * Date: 1/12/13
 * Time: 12:15 AM
 */
public class PasswordHashUtils {


    public static String generateSaltedHash(String password){
        return generateSaltedHash(password, null);
    }

    public static String generateSaltedHash(String password, String saltPhrase){
        return generateSaltedHash(password, saltPhrase, "SHA");
    }

    public static String generateSaltedHash(String password, String saltPhrase, String algorithm){
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);

            if (saltPhrase != null){
                md.update(saltPhrase.getBytes());
                byte[] salt = md.digest();

                md.reset();
                md.update(password.getBytes());
                md.update(salt);
            }else{
                md.update(password.getBytes());
            }

            byte[] raw = md.digest();
            return Base64.encodeBytes(raw);
//            return "";
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
