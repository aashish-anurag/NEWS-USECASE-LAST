package com.jwt.jwt_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class JwtSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtSpringApplication.class, args);
	}

}
