package com.inventory.alertsystem;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableRabbit
@SpringBootApplication
public class InventoryAlertSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryAlertSystemApplication.class, args);
	}

}
