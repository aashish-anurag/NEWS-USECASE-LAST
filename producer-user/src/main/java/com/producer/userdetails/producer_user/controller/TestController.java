package com.producer.userdetails.producer_user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class TestController {
    @GetMapping("/next")
    public String test(){
            return "test";
    }
}
