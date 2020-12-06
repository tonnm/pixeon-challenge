package com.pixeon.HealthCareInstitution;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HealthCareInstitutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthCareInstitutionApplication.class, args);

	}
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

}
