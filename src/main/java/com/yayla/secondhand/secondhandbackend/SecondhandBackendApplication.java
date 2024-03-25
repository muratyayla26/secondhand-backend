package com.yayla.secondhand.secondhandbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SecondhandBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondhandBackendApplication.class, args);
	}

}
