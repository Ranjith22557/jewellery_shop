package com.rtech.jewellery;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication()
public class JewelleryApplication {

	public static void main(String[] args) {

		// Load environment variables from .env
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		// Inject into system properties so Spring can pick them up
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		SpringApplication.run(JewelleryApplication.class, args);
	}
}
