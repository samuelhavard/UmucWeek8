/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Week8Fixed;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Samuel Havard
 */
public class PassCrypt {


    /**
     * 
     * @param pass is the password that the user entered
     * @param salt is the salt retrieved from the database
     * @return the hashed and salted password
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     */
    public static String passCrypt(String pass, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {

    int iterations = 1000;
    char[] chars = pass.toCharArray();
    
    PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hash = skf.generateSecret(spec).getEncoded();
    
    BigInteger bi = new BigInteger(1, hash);
        String hex = bi.toString(16);
        int paddingLength = (hash.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
}
