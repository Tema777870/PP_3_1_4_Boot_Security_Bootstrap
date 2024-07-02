package ru.kata.spring.boot_security.demo.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService extends UserDetailsService {

    void save(User user, Set<String> roles);

    void update(User user);

    List<User> listAll();

    User get(int id);

    void delete(int id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
