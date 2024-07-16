package ru.kata.spring.boot_security.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class UserGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserIncorrectDataResponse> userNotFoundExceptionHandler(NoSuchUserException e) {
        UserIncorrectDataResponse response = new UserIncorrectDataResponse();
        response.setInfo(e.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectDataResponse> exceptionHandler(Exception e) {
        UserIncorrectDataResponse response = new UserIncorrectDataResponse();
        response.setInfo(e.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<UserIncorrectDataResponse> validationExceptionHandler(UserValidationException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        UserIncorrectDataResponse data = new UserIncorrectDataResponse();
        for (FieldError fieldError : fieldErrors) {
            data.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
