package com.vip.journalApp.service;

import com.vip.journalApp.entity.User;
import com.vip.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }



    public User updateUsers(String username, User user) {
        User userInDb = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Entry not found with username: " + username));
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());

        return userRepository.save(userInDb);
    }
}
