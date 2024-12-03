package com.medicalInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class MedicalDetailsApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MedicalDetailsApiApplication.class, args);
		
		System.out.println("done..");
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		
		return builder.sources(MedicalDetailsApiApplication.class);
		
		
		
	}

}
