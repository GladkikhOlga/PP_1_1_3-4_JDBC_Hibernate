package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.jaxb.SourceType;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


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

    private static SessionFactory sessionFactory;

     static {
         if (sessionFactory == null) {
             try {
                 Configuration configuration = new Configuration();
                 Properties settings = new Properties();

                 settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                 settings.put(Environment.URL, "jdbc:mysql://localhost:3306/usersdb?useSSL=false");
                 settings.put(Environment.USER, "dbuser");
                 settings.put(Environment.PASS, "1234");
                 settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                 settings.put(Environment.SHOW_SQL, "true");
                 settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");


                 configuration.setProperties(settings);

                 configuration.addAnnotatedClass(User.class);

                 ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                         .applySettings(configuration.getProperties()).build();

                 sessionFactory = configuration.buildSessionFactory(serviceRegistry);
             } catch (Exception e) {
                 e.printStackTrace();
                 System.out.println("Connection ERROR");
             }
         }
     }
         public static SessionFactory getSessionFactory() {
            return sessionFactory;
     }

}
