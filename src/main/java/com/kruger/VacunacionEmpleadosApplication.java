package com.kruger;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class VacunacionEmpleadosApplication {

	public static void main(String[] args) {
		//SpringApplication.run(VacunacionEmpleadosApplication.class, args);
		SpringApplication app = new SpringApplication(VacunacionEmpleadosApplication.class);
		app.setDefaultProperties(Collections
		          .singletonMap("server.port", "8090"));
		        app.run(args);
	}

}
