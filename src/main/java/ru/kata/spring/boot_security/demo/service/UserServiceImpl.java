package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserDao;

import java.util.List;

@Service
public class UserServiceImpl implements UserServiceInf {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public void add(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        userDao.add(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Transactional
    @Override
    public void updateUser(User user, User existingUser) {
        userDao.updateUser(user, existingUser);
    }

    @Transactional
    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }


    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

}
