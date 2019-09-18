/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxis.lee.utils;

import sun.management.counter.perf.PerfInstrumentation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author oXCToo
 */
public class ConnectionUtil {
    private static String url ;
    private static String username;
    private static String password;
    private static String driver;
    Connection conn = null;
    public static Connection conDB()
    {
        Map<String, String> conf = DBConfigUtil.getConf();
        if(conf ==null){
            return null;
        }
        url = conf.get("url");
        username = conf.get("username");
        password = conf.get("password");
        driver = conf.get("driver");
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("ConnectionUtil : "+ex.getMessage());
           return null;
        }
    }


}
