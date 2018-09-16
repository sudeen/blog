package com.sudin.blog.controllers;

import com.sudin.blog.entities.Role;
import com.sudin.blog.entities.User;
import com.sudin.blog.pojos.UserRegistration;
import com.sudin.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public String register(@RequestBody UserRegistration userRegistration){
        if (!userRegistration.getPassword().equals(userRegistration.getPasswordConfirmation()))
            return "Password do not match";
        else if (userService.getUser(userRegistration.getUsername())!=null)
            return "User already exists";

        userService.save(new User(userRegistration.getUsername(),userRegistration.getPassword(), Arrays.asList(new Role("USER"))));
        return "User Created";
    }

    @GetMapping(value = "/usersList")
    public List<User> users(){
        return userService.getAllUsers();
    }
}
