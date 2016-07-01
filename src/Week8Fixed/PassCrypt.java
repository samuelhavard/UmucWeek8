/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Week8Fixed;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel Havard
 */
public class PassCrypt {

    private static MessageDigest md;
    private static String hashSalt;

    /**
     * 
     * @param pass is the password that the user entered
     * @param salt is the salt retrieved from the database
     * @return the hashed and salted password
     */
    public static String passCrypt(String pass, double salt) {
        try {
            hashSalt = pass + salt;
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = hashSalt.getBytes("UTF8");
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(PassCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
