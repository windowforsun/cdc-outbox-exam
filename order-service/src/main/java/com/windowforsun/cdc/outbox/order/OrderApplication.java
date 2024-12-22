package com.windowforsun.cdc.outbox.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.windowforsun.cdc.outbox")
@ComponentScan(basePackages = "com.windowforsun.cdc")
public class OrderApplication {
	public static void main(String... args) {
		SpringApplication.run(OrderApplication.class);
	}
}
