/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Week8Fixed;

import java.io.Serializable;

/**
 *
 * @author Samuel Havard
 */
public final class User implements Serializable{
    
    private final String username;
    private final int auth;
    private static final long serialVersionUID = 5462223600l;

    User(String username, int auth) {
        this.username = username;
        this.auth = auth;
    }
    
    /**
     * 
     * @return the username as a string
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * 
     * @return the authorization level as an integer
     */
    public int getAuth() {
        return auth;
    }
}
