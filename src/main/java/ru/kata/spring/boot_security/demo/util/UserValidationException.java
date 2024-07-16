package ru.kata.spring.boot_security.demo.util;

import org.springframework.validation.BindingResult;

public class UserValidationException extends RuntimeException {

    private final BindingResult bindingResult;

    public UserValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {

        return bindingResult;
    }
}
