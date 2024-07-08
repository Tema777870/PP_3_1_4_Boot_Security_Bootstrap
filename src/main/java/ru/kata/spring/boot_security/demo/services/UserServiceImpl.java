package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Transactional
    public void save(User user) {
        //  Optional<User> userFromDB = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.addRole(roleRepository.getById(1));
        userRepository.save(user);
    }

    @Transactional
    public void update(User user) {
        User userFromDB = userRepository.findById(user.getId()).get();
        String password = userFromDB.getPassword();
        Set<Role> roles = userFromDB.getRoles();
        user.setRoles(roles);

        if (!password.equals(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }


    public List<User> listAll() {

        return userRepository.findAll();
    }

    public User get(int id) {

        return userRepository.findById(id).get();
    }

    @Transactional
    public void delete(int id) {

        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    public Optional<User> findByUsername(String username) {
        User user = userRepository.findByEmail(username);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public void saveAllUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    public void deleteAllUsers(List<User> users) {
        userRepository.deleteAll(users);
    }


}
