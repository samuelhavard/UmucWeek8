/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Week8Fixed;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel Havard
 */
public class PassSalt {
    private static SecureRandom secureRandom;
    
    /**
     * 
     * @return a unique salt that is to be stored in the database with
     * the users information.
     */
    public static Double getSalt(){
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[512];
            secureRandom.nextBytes(bytes);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PassSalt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return secureRandom.nextDouble();
    }
}
