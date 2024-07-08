package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.models.UserEditDTO;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;


@Controller
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepo;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepo) {
        this.userService = userService;
        this.roleRepo = roleRepo;
    }


    @GetMapping("/admin")
    public String home(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("users", userService.listAll());
        model.addAttribute("roles", roleRepo.findAll());
        model.addAttribute("new_user", new User());
        model.addAttribute("usersForm", new UserEditDTO(userService.listAll()));
        return "admin/admin";
    }


    @RequestMapping(value = {"/admin/new", "/admin/admin/new"}, method = RequestMethod.POST)
    public String createUser(@ModelAttribute("user") @Valid User user) {

        userService.save(user);
        return "redirect:/admin/";
    }

    @RequestMapping(value = {"/admin/edit", "/admin/admin/edit"}, method = RequestMethod.POST)
    public String editUser(Model model, @ModelAttribute UserEditDTO userEditDTO) {
        userService.saveAllUsers(userEditDTO.getUsers());
        model.addAttribute("roles", roleRepo.findAll());
        return "redirect:/admin/";
    }

    @RequestMapping(value = {"/admin/delete", "/admin/admin/delete"}, method = RequestMethod.POST)
    public String removeUser(@ModelAttribute UserEditDTO userEditDTO) {
        userService.deleteAllUsers(userEditDTO.getUsers());
        return "redirect:/admin/";
    }
}
