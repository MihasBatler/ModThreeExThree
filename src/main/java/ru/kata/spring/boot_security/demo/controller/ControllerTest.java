package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.UserService;


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

    @GetMapping("/access")
    public String accessDenied() {
        return "access";
    }

    @GetMapping("/mentor")
    public String messageToMentor() {
        return "mentor";
    }
}
