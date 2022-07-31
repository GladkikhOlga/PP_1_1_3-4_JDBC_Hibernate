package jm.task.core.jdbc;
import com.mysql.jdbc.*;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Olga", "Ivanova", (byte) 20);
        userService.saveUser("Andrey", "Bazanov", (byte) 25);
        userService.saveUser("Sergey", "Glushko", (byte) 31);
        userService.saveUser("Oleg", "Konovalov", (byte) 38);
        List<User> usersList = userService.getAllUsers();
        for (User user : usersList) {
            System.out.println(user);
        }

        userService.removeUserById(1);
        List<User> newUsersList = userService.getAllUsers();
        for (User user : newUsersList) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        List<User> newUsersList2 = userService.getAllUsers();
        for (User user : newUsersList2) {
            System.out.println(user);
        }

       userService.dropUsersTable();
    }
}


