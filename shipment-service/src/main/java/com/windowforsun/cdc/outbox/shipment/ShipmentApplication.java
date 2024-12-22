package com.windowforsun.cdc.outbox.shipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.windowforsun.cdc.outbox")
@ComponentScan(basePackages = "com.windowforsun.cdc")
public class ShipmentApplication {
	public static void main(String... args) {
		SpringApplication.run(ShipmentApplication.class);
	}
}
