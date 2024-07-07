package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    private final UserService userService;


    @Autowired
    public UserValidator(UserService userService) {

        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> byEmail = userService.findByEmail(user.getEmail());
     //   Optional<User> byUsername = userService.findByUsername(user.getUsername());
        if (user.getId() == 0) {
          //  validateUsername(byUsername, errors);
            validateEmail(byEmail, errors);
        } else if (byEmail.isPresent() && byEmail.get().getId() != user.getId()) {
            validateEmail(byEmail, errors);
        }
//        } else if (byUsername.isPresent() && byUsername.get().getId() != user.getId()) {
//            validateUsername(byUsername, errors);
//        }
    }

    private void validateEmail(Optional<User> user, Errors errors) {
        if (user.isPresent()) {
            errors.rejectValue("email", "", "This email is already taken");
        }
    }

//    private void validateUsername(Optional<User> user, Errors errors) {
//        if (user.isPresent()) {
//            errors.rejectValue("username", "", "This username is already taken");
//        }
//    }
}
