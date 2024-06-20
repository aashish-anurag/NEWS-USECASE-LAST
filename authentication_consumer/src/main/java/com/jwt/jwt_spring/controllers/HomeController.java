package com.jwt.jwt_spring.controllers;

import com.jwt.jwt_spring.models.User;
import com.jwt.jwt_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public List<User> getUser(){
            System.out.println("getting users");
            return userService.getUsers();
    }

    @RequestMapping("/current-user")
    public String getCurrentUser(Principal principal){
        return principal.getName();
    }

}
