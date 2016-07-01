/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Week8Fixed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel Havard
 */
public class DBConn {
    private static Connection conn;

    /**
     * @return an instance of a database Connection using the DbModelSalt class
     * as parameters.
     */
    public static Connection getConnection() {
        
        DbModel dbm = new DbModel();
        try {
            conn = DriverManager.getConnection(
                    dbm.getDriver(),
                    dbm.getUser(),
                    dbm.getPass());
        } catch (SQLException ex) {
            Logger.getLogger(UserAuth.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
