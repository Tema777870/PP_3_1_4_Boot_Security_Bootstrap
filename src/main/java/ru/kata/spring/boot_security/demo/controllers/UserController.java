package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
//@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

        @GetMapping ("/user")

    //После настройки авторизации добавить сюда логику, чтобы ID тянулся из Principal залогиненного юзера
    public String showUser(@RequestParam(name = "userId") int userId, Model model) {
            User user = userService.get(userId);
            model.addAttribute("user", user);
        return "users/user";
    }

//    @GetMapping("user/index")
//    public String index(int userId, Model model) {
//        User user = userService.get(userId);
//        model.addAttribute("user", user);
//        return "users/index";
//    }
}
