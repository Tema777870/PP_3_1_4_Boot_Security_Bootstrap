package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }


    @GetMapping
    public String home(Model model) {
        model.addAttribute("users", userService.listAll());
        return "admin/index";
    }

    @GetMapping("/new")
    public String newCustomerForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, @RequestParam(value = "userRoles", required = false) Set<String> role) {
        System.out.println(role);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "admin/new";
        userService.save(user, role);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String showUser(@RequestParam(name = "userId") int userId, Model model) {
        User user = userService.get(userId);
        model.addAttribute("user", user);
        return "admin/show";
    }

    @GetMapping("/edit")
    public String showEditUser(@RequestParam(name = "userId") int userId, Model model) {
        User user = userService.get(userId);
        model.addAttribute("user", user);
        return "admin/edit";
    }

    @PatchMapping("/edit")
    public String editUser(@RequestParam(name = "userId") int userId,
                           @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String deleteUserPage(@RequestParam(name = "userId") int userId,
                                 Model model) {
        User user = userService.get(userId);
        model.addAttribute("user", user);
        return "admin/delete";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(name = "userId") int userId, @ModelAttribute("user") User user) {
        userService.delete(userId);
        return "redirect:/admin";
    }
}
