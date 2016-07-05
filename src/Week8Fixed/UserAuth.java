/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Week8Fixed;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel Havard
 */
final public class UserAuth {

    private String username = "";
    private String password = "";
    PreparedStatement ps = null;
    ResultSet rs = null;

    UserAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * readDB uses the getConnection and getStatement methods to connect to the
     * database and read the results of a prepared statement to a result set.
     *
     * @return
     * @throws SQLException
     * @throws UnsupportedEncodingException
     */
    public boolean readDB() throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
        }

        Connection conn = DBConn.getConnection();
        ResultSet resSet = getStatement(conn, username);

        if (resSet.next()) {
            /*
             * get the unique salt from the database based off of the username
             */
            byte[] salt = resSet.getBytes("Salt");

            /*
             * get the user input password and hash / salt it.
             */
            String hashPass;
            hashPass = PassCrypt.passCrypt(password, salt);
            System.out.println(hashPass);

            /*
             * convert both the user input text password that has been salted and hashed
             * and the stored salted and hashed password from the database and compare them. 
             */
            String dbPass = resSet.getString("Password");
            byte[] hashByte = hashPass.getBytes("UTF8");
            byte[] passByte = dbPass.getBytes("UTF8");

            return slowEquals(hashByte, passByte);

        } else {
            return false;
        }
    }

    /**
     * The code uses the XOR "^" operator to compare integers for equality,
     * instead of the "==" operator. The reason we need to use XOR instead of
     * the "==" operator to compare integers is that "==" is usually
     * translated/compiled/interpreted as a branch. The branching makes the code
     * execute in a different amount of time depending on the equality of the
     * integers and the CPU's internal branch prediction state.
     *
     * Comparing the hashes in "length-constant" time ensures that an attacker
     * cannot extract the hash of a password in an on-line system using a timing
     * attack, then crack it off-line.
     *
     * source: https://crackstation.net/hashing-security.htm
     *
     * @param a is the byte array of the hashed user input
     * @param b is the byte array of the hashed password from the DB.
     * @return true if they are equal and false otherwise
     */
    private boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    /**
     * @param conn uses connection to query database
     * @param username queries the database for the username input by the user
     * @return a result set based off of user input
     * @throws SQLException
     */
    private ResultSet getStatement(Connection conn, String username) {

        String safeQuery = "SELECT * FROM project1.user WHERE Username=?";
        try {
            ps = conn.prepareStatement(safeQuery);
            ps.setString(1, username);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(UserAuth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    /**
     *
     * @return the authorization level from the database as an integer
     * @throws SQLException
     */
    public int getAuth() throws SQLException {
        int authLevel = 0;
        Connection conn = DBConn.getConnection();
        ResultSet resSet = getStatement(conn, username);
        if (resSet.next()) {
            authLevel = resSet.getInt("AuthLevel");
        }
        return authLevel;
    }
}
