package ru.kata.spring.boot_security.demo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
//@RequestMapping("/users")
public class AdminController {

    private final UserService userService;
    private final UserValidator userValidator;

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
                         BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "admin/new";
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/user")
    public String showUser(@RequestParam(name = "userId") int userId, Model model) {
        User user = userService.get(userId);
        model.addAttribute("user", user);
        return "admin/show";
    }

    @GetMapping("admin/edit")
    public String showEditUser(@RequestParam(name = "userId") int userId, Model model) {
        User user = userService.get(userId);
        model.addAttribute("user", user);
        return "admin/edit";
    }

    @PatchMapping("admin/edit")
    public String editUser(@RequestParam(name = "userId") int userId,
                           @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        // userValidator.validate(user, bindingResult); эта строка кода не дает менять поля без изменения email, пока не придумал как пофиксить
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("admin/delete")
    public String deleteUserPage(@RequestParam(name = "userId") int userId,
                                 Model model) {
        User user = userService.get(userId);
        model.addAttribute("user", user);
        return "admin/delete";
    }

    @DeleteMapping("admin/delete")
    public String deleteUser(@RequestParam(name = "userId") int userId, @ModelAttribute("user") User user) {
        userService.delete(userId);
        return "redirect:/users";
    }
}
