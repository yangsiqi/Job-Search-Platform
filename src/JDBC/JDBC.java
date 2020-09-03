package JDBC;

import javax.xml.transform.Result;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBC {
    private static String url;
    private static String driver;
    private static String username;
    private static String password;
    static {
        try {
            InputStream input=JDBC.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties=new Properties();
            properties.load(input);
            driver=properties.getProperty("driver");
            url=properties.getProperty("url");
            username=properties.getProperty("username");
            password=properties.getProperty("password");
            Class.forName(driver);
            System.out.println("Load driver");
            System.out.println("link to database");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static Connection connect(){
        try {
            return DriverManager.getConnection(url,username,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public static void close(Connection c, ResultSet r, Statement s){
        try {
            if(c!=null){
                c.close();
                c=null;
            }
            if(s!=null){
                s.close();
                s=null;
            }
            if(r!=null){
                r.close();
                r=null;
            }
        }
        catch (SQLException throwables) {
                throwables.printStackTrace();
        }
    }
}
