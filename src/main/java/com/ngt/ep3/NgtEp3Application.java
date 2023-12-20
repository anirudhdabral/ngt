package com.ngt.ep3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ngt.ep3")
public class NgtEp3Application {
	public static void main(String[] args) {
		SpringApplication.run(NgtEp3Application.class, args);
	}
}
