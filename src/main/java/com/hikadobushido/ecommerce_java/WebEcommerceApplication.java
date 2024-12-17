package com.hikadobushido.ecommerce_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebEcommerceApplication.class, args);
	}

}
