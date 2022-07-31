package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    //конструктор без параметров
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement()) {
            String SQL = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT AUTO_INCREMENT, " +
                    " name VARCHAR(40) NOT NULL, " +
                    " lastname VARCHAR(40) NOT NULL, " +
                    " age TINYINT NOT NULL, " +
                    "PRIMARY KEY (id))";

            stmt.execute(SQL);
            System.out.println("Таблица успешно создана");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Таблица не создана или уже существует");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement()) {
            String SQL = "DROP TABLE IF EXISTS users";
            stmt.executeUpdate(SQL);
            System.out.println("Таблица в базе данных удалена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Таблица не удалена или такая таблица не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO users(name,lastName, age) VALUES(?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Пользователь не добавлен");
        }

    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            System.out.println("User с id = " + id + " был удален из таблицы");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка удаления или User с этим id не существует");
        }
    }

    public List<User> getAllUsers() {
        String SQL = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement()) {
            String SQL = "TRUNCATE users";
            stmt.executeUpdate(SQL);
            System.out.println("Таблица успешно очищена.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Таблица не очищена.");
        }

    }
}
