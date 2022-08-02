package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;
    Transaction transaction = null;

    public UserDaoHibernateImpl() {

        sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String SQL = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT AUTO_INCREMENT, " +
                    " name VARCHAR(40) NOT NULL, " +
                    " lastname VARCHAR(40) NOT NULL, " +
                    " age TINYINT NOT NULL, " +
                    "PRIMARY KEY (id))";
            session.createSQLQuery(SQL).executeUpdate();
            transaction.commit();
            System.out.println("Таблица успешно создана");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Таблица не создана или уже существует");
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String SQL = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(SQL).executeUpdate();
            transaction.commit();
            System.out.println("Таблица успешно удалена.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Таблица не удалена или такая таблица не существует");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Пользователь не добавлен");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("Пользователь был удален из таблицы");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Пользователь не был удален или такой пользователь не существует");
        }


    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String SQL = "TRUNCATE TABLE users";
            session.createSQLQuery(SQL).executeUpdate();
            transaction.commit();
            System.out.println("Таблица успешно очищена.");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Таблица не очищена.");
        }
    }
}
