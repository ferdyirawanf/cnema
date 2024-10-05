package com.alpine.cnema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CnemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnemaApplication.class, args);
	}
}
