package com.sudin.blog.controllers;

import com.sudin.blog.entities.Role;
import com.sudin.blog.entities.User;
import com.sudin.blog.pojos.UserRegistration;
import com.sudin.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @PostMapping(value = "/register")
    public String register(@RequestBody UserRegistration userRegistration) {
        if (!userRegistration.getPassword().equals(userRegistration.getPasswordConfirmation()))
            return "Password do not match";
        else if (userService.getUser(userRegistration.getUsername()) != null)
            return "User already exists";

        //Checking for non alphanumerical characters in the username.
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        if (pattern.matcher(userRegistration.getUsername()).find())
            return "No special characters are allowed in the username";

        userService.save(new User(userRegistration.getUsername(), userRegistration.getPassword(), Arrays.asList(new Role("USER"), new Role("ACTUATOR"))));
        return "User Created";
    }

    /*@GetMapping(value = "/usersList")
    public List<User> users() {
        return userService.getAllUsers();
    }*/


    @GetMapping(value = "/users")
    public List<User> users(){
        return userService.getAllUsers();
    }

    @GetMapping(value = "/logouts")
    public void logout(@RequestParam(value = "access_token") String accessToken){
        tokenStore.removeAccessToken(tokenStore.readAccessToken(accessToken));
    }

    @GetMapping(value ="/getUsername")
    public String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
