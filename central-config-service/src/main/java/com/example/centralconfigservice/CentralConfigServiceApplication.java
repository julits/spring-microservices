package com.example.centralconfigservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableConfigServer
@EnableEurekaClient
@SpringBootApplication
public class CentralConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralConfigServiceApplication.class, args);
	}

}
