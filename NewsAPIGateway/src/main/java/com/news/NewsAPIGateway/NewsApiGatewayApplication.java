package com.news.NewsAPIGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NewsApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsApiGatewayApplication.class, args);
	}

}
