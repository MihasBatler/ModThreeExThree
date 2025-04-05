package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;

@RequestMapping
@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService,
                           RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin/main")
    public String printTable(Model model) {
        List<User> myUser = userService.getAll();
        model.addAttribute("users", myUser);
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
        model.addAttribute("findUser", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "updateForm";
    }

    @PatchMapping(value = "/admin/update")
    public String update(@ModelAttribute("upUser") User userForm,
                         @RequestParam("roleIds") List<Long> roleIds) {
        User existingUser = userService.getUserById(userForm.getId());
        userService.updateUser(userForm, existingUser);
        List<Role> selectedRole = roleService.getRolesByIds(roleIds);
        existingUser.getRoles().clear();
        existingUser.getRoles().addAll(selectedRole);
        userService.add(existingUser);
        return "redirect:/admin/main";
    }


    @GetMapping(value = "/admin/new")
    public String addUserForm(Model model) {
        User user = new User();
        model.addAttribute("newUser", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "addForm";
    }

    @PostMapping(value = "/admin/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("roleIds") List<Long> roleIds) {
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

