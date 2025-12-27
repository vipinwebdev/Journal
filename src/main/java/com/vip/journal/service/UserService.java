package com.vip.journal.service;

import com.vip.journal.entity.User;
import com.vip.journal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveNewUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        return userRepository.save(user);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUsers(String username, User user) {
        User userInDb = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Entry not found with username: " + username));
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(userInDb);
    }

    public User saveAdmin(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(List.of("USER","ADMIN"));
        return userRepository.save(user);
    }
}
