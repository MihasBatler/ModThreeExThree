package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserDao {

    void add(User user);

    List<User> getAll();

    void updateUser(User user, User existingUser);

    void removeUserById(long id);

    User getUserById(long id);

}
