package com.sample.kvservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication(scanBasePackages="com.sample")
public class PostgressApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PostgressApplication.class, args);
	}

}

