package com.hp.tvbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class TVBook {

	public static void main(String[] args) {
		SpringApplication.run(TVBook.class, args);
	}
}
