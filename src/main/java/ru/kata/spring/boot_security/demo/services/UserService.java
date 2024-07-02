package ru.kata.spring.boot_security.demo.service;




import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    List<User> listAll();

    User get(int id);

    void delete(int id);

    User findByEmail(String email);
}
