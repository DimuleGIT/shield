package com.cujo.iot.shield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cujo.iot.shield")
public class ShieldApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShieldApplication.class, args);
	}

}
