package com.example.chatapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class ChatapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatapplicationApplication.class, args);
	}

}
