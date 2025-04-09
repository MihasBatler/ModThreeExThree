package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void add(User user);

    List<User> getAll();

    void updateUser(Long id, User user, List<Long> roleIds);

    void removeUserById(long id);

    User getUserById(long id);

}
