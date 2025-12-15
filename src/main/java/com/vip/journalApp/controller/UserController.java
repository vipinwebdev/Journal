package com.vip.journalApp.controller;

import com.vip.journalApp.entity.User;
import com.vip.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAll() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User save(@RequestBody User user) {
        return userService.save(user);
    }
    @PutMapping("/{username}")
    public User update(@RequestBody User user, @PathVariable String username) {
        return userService.updateUsers(username,user);
    }
}
