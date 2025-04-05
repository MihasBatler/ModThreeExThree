package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public Init(UserService userService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    @Transactional
    public void init() {
        Role adminRole = roleService.getRoleByName("ROLE_ADMIN");
        Role userRole = roleService.getRoleByName("ROLE_USER");

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(bCryptPasswordEncoder.encode("12345"));
        admin.setName("Вардан");
        admin.setLastName("Хихикян");
        admin.setAge(35);
        admin.setAddress("Лондон");
        admin.setEnabled(true);
        admin.setRoles(Set.of(adminRole, userRole));
        userService.add(admin);


        User user = new User();
        user.setUsername("user");
        user.setPassword(bCryptPasswordEncoder.encode("user123"));
        user.setName("Ольга");
        user.setLastName("Карнавал");
        user.setAge(25);
        user.setAddress("Псков");
        user.setEnabled(true);
        user.setRoles(Set.of(userRole));
        userService.add(user);
    }
}

