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

import java.util.*;
import java.util.stream.Collectors;


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
    public void save(User user, Set<String> roles) {
        user.setRoles(getUserRoles(roles));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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

    private Set<Role> getUserRoles(Set<String> roles) {
        if (roles.isEmpty()) {
            roles.add("1");
        }
        Set<Integer> roleIds = roles.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
        return new HashSet<>(roleRepository.findAllById(roleIds));

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

    public Optional<User> findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    public Optional<User> findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

}
