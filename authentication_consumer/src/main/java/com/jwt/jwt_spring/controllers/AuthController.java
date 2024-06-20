package com.jwt.jwt_spring.controllers;

import com.jwt.jwt_spring.DTOs.JwtRequest;
import com.jwt.jwt_spring.DTOs.JwtResponse;
import com.jwt.jwt_spring.kafka.KafkaConsumer;
import com.jwt.jwt_spring.models.User;
import com.jwt.jwt_spring.security.JwtHelper;
import com.jwt.jwt_spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/test")
    public String test(){
        return "test auth";
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper helper;

    @Autowired
    private KafkaConsumer consumer;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
    this.doAuthenticate(request.getEmail(), request.getPassword());

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
    String token = this.helper.generateToken(userDetails);

    JwtResponse response = JwtResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();
    return new ResponseEntity<>(response, HttpStatus.OK);

    }

   private void doAuthenticate(String email,String passwrod){
       UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,passwrod);
       try{
           manager.authenticate(authenticationToken);
       }catch(BadCredentialsException e){
           throw new BadCredentialsException(" Invalid Username or Password !!");
       }
    }
    //
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(){
        return "Credentials Invalid !!";
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        log.info("=====INSIDE CREATE USER AFTER CONSUMING====");
        // login consuming data from kafka and will save data after login
        consumer.consumeJson(user);
        log.info("====After consumed the User====");

        return ResponseEntity.ok(userService.createUser(user));
    }

}
