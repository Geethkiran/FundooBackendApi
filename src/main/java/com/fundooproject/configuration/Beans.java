package com.fundooproject.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This class contains Beans. These Beans are used in other classes for creating
 * objects.
 * 
 * @author Geeth
 *
 */
@EnableAutoConfiguration
@Configuration
public class Beans {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}
}