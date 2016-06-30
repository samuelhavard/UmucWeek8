/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Samuel Havard
 */
final public class DbModel {

    private String fileName = "C:\\Users\\samue_000\\Documents\\NetBeansProjects\\Week6Java\\src\\week6javaFixed\\dbConfig.txt";
    private Map<String, String> HM;

    DbModel() {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            HM = stream.collect(Collectors.toMap(k -> k.split("=")[0], v -> v.split("=")[1]));
        } catch (IOException e) {
            System.out.println("Error Reading Config File.  Please Alert Your System Admin.");
        }
    }
    /**
     * 
     * @return JDBC Driver URL from dbConfig.txt for use in DbConn
     */
    public String getDriver() {
        return HM.get("DRIVER");
    }
    /**
     * 
     * @return Username from dbConfig.txt for use in DbConn
     */
    public String getUser() {
        return HM.get("USER");
    }
    /**
     * 
     * @return Password from dbConfig.txt for use inDbConn
     */
    public String getPass() {
        return HM.get("PASS");
    }
}
