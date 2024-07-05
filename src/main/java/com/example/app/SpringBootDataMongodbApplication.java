package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //This is an annotation for spring boot application
public class SpringBootDataMongodbApplication {

	public static void main(String[] args) { //function returns nothing and is public 
		SpringApplication.run(SpringBootDataMongodbApplication.class, args); //runs the given class
	} 

}
