package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;


@RequestMapping
@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/")
    public String printTable() {
        return "index";
    }


    @GetMapping(value = "/user")
    public String getUser(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = "/register")
    public String addUserRegister(Model model) {
        User user = new User();
        model.addAttribute("newUserReg", user);
        return "formRegister";
    }

    @PostMapping(value = "/add")
    public String addRegister(@ModelAttribute("newUserReg") User user) {
        Role userRole = roleService.getRoleByName("ROLE_USER");
        user.setRoles(Set.of(userRole));
        userService.add(user);
        return "redirect:/user";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }
}
