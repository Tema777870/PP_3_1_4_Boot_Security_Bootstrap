package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;


@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Transactional
    public void save(User user) {

        userRepository.save(user);
    }

    public List<User> listAll() {

        return (List<User>) userRepository.findAll();
    }

    public User get(int id) {

        return userRepository.findById(id).get();
    }

    @Transactional
    public void delete(int id) {

        userRepository.deleteById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
