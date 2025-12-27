package com.vip.journal.controller;

import com.vip.journal.entity.User;
import com.vip.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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


    @PutMapping
    public User update(@RequestBody User user) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.updateUsers(username,user);
    }


}
