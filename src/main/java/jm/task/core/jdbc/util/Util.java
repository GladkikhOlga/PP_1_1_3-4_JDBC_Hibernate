package jm.task.core.jdbc.util;

import org.hibernate.boot.jaxb.SourceType;

import java.sql.Connection;
import java.sql.DriverManager;


public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/usersdb";
    private static final String USERNAME = "dbuser";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection OK");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }


}
