package ru.kata.spring.boot_security.demo.util;

import java.util.HashMap;
import java.util.Map;

public class UserIncorrectDataResponse {
    private String info;
    private long timestamp;
    private final Map<String, String> fieldErrors;

    public UserIncorrectDataResponse() {

        fieldErrors = new HashMap<>();
    }

    public void addError(String fieldName, String errorMessage) {

        fieldErrors.put(fieldName, errorMessage);
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public String getInfo() {

        return info;
    }

    public void setInfo(String info) {

        this.info = info;
    }

    public long getTimestamp() {

        return timestamp;
    }

    public void setTimestamp(long timestamp) {

        this.timestamp = timestamp;
    }
}
