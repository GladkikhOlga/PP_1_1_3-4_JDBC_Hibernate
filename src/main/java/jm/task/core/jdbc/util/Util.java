package jm.task.core.jdbc.util;

import org.hibernate.boot.jaxb.SourceType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {


    static Connection connection = null;

    static {
        String URL = "jdbc:mysql://localhost:3306/usersdb";
        String USERNAME = "dbuser";
        String PASSWORD = "1234";
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection OK");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
