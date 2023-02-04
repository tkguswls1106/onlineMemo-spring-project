package com.shj.onlinememospringproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OnlinememoSpringProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinememoSpringProjectApplication.class, args);
	}

}
