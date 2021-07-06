package com.capitole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CapitoleApplication {
	/**
	 * Runs h2 db and tomcat with a REST API.
	 */
	public static void main(String[] args) {
		SpringApplication.run(CapitoleApplication.class, args);
	}
}
