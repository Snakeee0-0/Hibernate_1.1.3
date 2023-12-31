package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.connection;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable()  {
        try {
            PreparedStatement preparedStatement  = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Users(" +"id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, name varchar, lastName varchar, age smallint)");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Такая таблица уже существует");}
    }

    public void dropUsersTable() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS Users");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Такая таблица не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TABLE Users id");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Такого пользователя нет");
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");
                users.add(new User(id, name, lastName, (byte) age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String query = "DELETE FROM users";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
