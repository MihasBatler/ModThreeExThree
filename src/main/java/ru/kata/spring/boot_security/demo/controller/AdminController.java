package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.*;

@RequestMapping
@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserRepository userRepository;

    public AdminController(UserService userService,
                           RoleService roleService, UserRepository userRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/admin/main")
    public String printTable(Model model, Principal principal) {
        List<User> myUser = userService.getAll();
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("users", myUser);
        model.addAttribute("upUser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "mainAdminPage";
    }

    @GetMapping(value = "/admin/user")
    public String getUser(Model model, @RequestParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            model.addAttribute("errorMessage", "Пользователь с ID " + id + " не найден");
            return "form";
        }
        model.addAttribute("user", user);
        return "userOfAdmin";
    }

    @GetMapping(value = "/admin/form")
    public String showForm() {
        return "form";
    }

    @GetMapping(value = "/admin/updateForm")
    public String showUpdateForm(Model model, @RequestParam("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("upUser", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "mainAdminPage";
    }


    @PatchMapping(value = "/admin/update/{id}")
    public String updateUser(
            @PathVariable Long id,
            @ModelAttribute("upUser") User user,
            @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {

        User existingUser = userService.getUserById(id); //нов
        existingUser.getRoles();

        if (roleIds == null) {
            roleIds = Collections.emptyList();
        }

        userService.updateUser(id, user, roleIds);
        return "redirect:/admin/main";
    }


    @GetMapping(value = "/admin/new")
    public String addUserForm(Model model) {
        User user = new User();
        user.setRoles(new HashSet<>()); //н
        model.addAttribute("newUser", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "mainAdminPage";
    }

    @PostMapping(value = "/admin/add")
    public String addUser(@ModelAttribute("newUser") User user, @RequestParam("roleIds") List<Long> roleIds) {
        List<Role> selectedRole = roleService.getRolesByIds(roleIds);
        user.setRoles(new HashSet<>(selectedRole));
        userService.add(user);
        return "redirect:/admin/main";
    }


    @DeleteMapping("/admin/delete")
    public String remove(Model model, @RequestParam("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin/main";
    }

    @GetMapping(value = "/admin")
    public String print() {
        return "admin";
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

