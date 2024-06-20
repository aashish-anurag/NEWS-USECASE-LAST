package com.ebelemgnegre.ApiGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/newsServiceFallback")
    public String userServiceFallback() {
        return "News service is currently unavailable. Please try again later.";
    }

    @GetMapping("/wishlistServiceFallback")
    public String ServiceFallback() {
        return "News wishlist service is currently unavailable. Please try again later.";
    }
}
