package fr.miage.m2.bankprojectcarte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class BankprojectCarteApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankprojectCarteApplication.class, args);
	}
}
