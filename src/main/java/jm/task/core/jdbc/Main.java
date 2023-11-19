package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("John", "Doe", (byte) 25);
        System.out.println("User with name John was added to the database");

        userService.saveUser("Jane", "Smith", (byte) 30);
        System.out.println("User with name Jane was added to the database");

        userService.saveUser("Mike", "Johnson", (byte) 35);
        System.out.println("User with name Mike was added to the database");

        userService.saveUser("Anna", "Davis", (byte) 40);
        System.out.println("User with name Anna was added to the database");

        System.out.println("All Users:");
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
