package ru.kata.spring.boot_security.demo.models;

import java.util.List;


public class UserEditDTO {
    private List<User> users;

    public UserEditDTO() {
    }

    public UserEditDTO(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
