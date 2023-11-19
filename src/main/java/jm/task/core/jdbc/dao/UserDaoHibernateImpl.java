package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Queue;



public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "CREATE TABLE users (id serial PRIMARY KEY, name VARCHAR(255), lastName VARCHAR(255), age smallint)";
            session.createSQLQuery(hql).executeUpdate();
            transaction.commit();
        } catch (Exception e){
            System.out.println("Произошла ошибка при создании таблицы");
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(hql).executeUpdate();
            transaction.commit();
        } catch (Exception e){
            System.out.println("Произошла ошибка при удалении таблицы");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Произошла ошибка при сохранении пользователей");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Произошла ошибка при удалении пользователя");
        }
    }

    @Override
    public List<User> getAllUsers() {

        try(Session session = sessionFactory.openSession()){
            String hql = "FROM User";
            Query query = session.createQuery(hql);
            return query.getResultList();
        } catch (Exception e){
            System.out.println("Произошла ошибка при получении списка пользователей");
            return Collections.emptyList();
        }

    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "DELETE FROM User";
            session.createQuery(hql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Произошла ошибка во время очистки таблицы");
        }
    }
}
