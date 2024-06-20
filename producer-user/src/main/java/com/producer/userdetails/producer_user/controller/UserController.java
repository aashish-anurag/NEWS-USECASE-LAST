package com.producer.userdetails.producer_user.controller;

import com.producer.userdetails.producer_user.dto.UserDto;
import com.producer.userdetails.producer_user.entity.User_Info;
import com.producer.userdetails.producer_user.producer.KafkaProducer;
import com.producer.userdetails.producer_user.service.KafkaSupplierService;
import com.producer.userdetails.producer_user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final KafkaSupplierService kafkaSupplierService;
    private final KafkaProducer jsonProducer;

    @GetMapping
    public String hello(){
        System.out.println("===Printing===");
        return "Hello";
    }

    @PostMapping("/create-user")
    public ResponseEntity<User_Info> createUser(@RequestBody User_Info user){
        System.out.println("====create user====");
        //logger.info("====create user======");

        User_Info usr = userService.createUser(user);
        if(usr != null) {
          //  logger.info("reached to save {}",usr);
            System.out.println("reached to save"+user);
           jsonProducer.sendMessage(user);
            System.out.println("After kafka calling");
           logger.info("====After Kafka send method in controller====");

        }
        return ResponseEntity.ok(usr);
    }


    @PostMapping("/json")
    public ResponseEntity<String> sendJsonMessage(@RequestBody User_Info user) {
       jsonProducer.sendMessage(user);
        return ResponseEntity.ok("JSON Message Sent to the Topic Successfully!");

    }

    @GetMapping("/test")
    public String test(){
        return "Welcome!!";
    }

}
