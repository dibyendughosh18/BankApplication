package com.abc.DemoLogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DemoLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoLoginApplication.class, args);
	}

}
