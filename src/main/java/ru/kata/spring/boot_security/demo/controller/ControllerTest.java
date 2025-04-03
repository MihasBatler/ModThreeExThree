package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RequestMapping
@Controller
public class ControllerTest {
    private final UserService userService;

    public ControllerTest(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String printTable() {
        return "index";
    }
    @GetMapping(value = "/admin")
    public String print() {
        return "admin";
    }

    @GetMapping(value = "/user")
    public String getUser(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "user";
    }

//    @GetMapping(value = "/register")
//    public String addUserRegister(Model model) {
//        User user = new User();
//        model.addAttribute("newUserReg", user);
//        return "formRegister";
//    }
//
//    @PostMapping(value = "/add")
//    public String addRegister(@ModelAttribute("newUserReg") User user) {
//        Role userRole = roleService.getRolesByName("ROLE_USER");
//        user.setRoles(Set.of(userRole));
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        userServiceInf.add(user);
//        return "redirect:/user";
//    }
}
