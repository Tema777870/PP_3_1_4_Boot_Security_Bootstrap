package ru.kata.spring.boot_security.demo.services;




import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean save(User user);

    List<User> listAll();

    User get(int id);

    void delete(int id);

    User findByEmail(String email);
}
