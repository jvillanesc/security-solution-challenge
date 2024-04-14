package com.encora.ibk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableWebFlux
@SpringBootApplication
@EnableReactiveFeignClients
public class IBKApplication {

	public static void main(String[] args) {
		SpringApplication.run(IBKApplication.class, args);
	}

}
