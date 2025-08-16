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
		String dbUrl = dotenv.get("DB_URL");
		if (dbUrl != null) System.setProperty("DB_URL", dbUrl);

		String dockerDbUrl = dotenv.get("DOCKER_DB_URL");
		if (dockerDbUrl != null) System.setProperty("DOCKER_DB_URL", dockerDbUrl);

		String dbUser = dotenv.get("DB_USERNAME");
		if (dbUser != null) System.setProperty("DB_USERNAME", dbUser);

		String dbPassword = dotenv.get("DB_PASSWORD");
		if (dbPassword != null) System.setProperty("DB_PASSWORD", dbPassword);

		String sid = dotenv.get("TWILIO_SID");
		if (sid != null) System.setProperty("TWILIO_SID", sid);

		String authToken = dotenv.get("TWILIO_AUTH_TOKEN");
		if (authToken != null) System.setProperty("TWILIO_AUTH_TOKEN", authToken);

		String twilioNumber = dotenv.get("TWILIO_NUMBER");
		if (twilioNumber != null) System.setProperty("TWILIO_NUMBER", twilioNumber);


		SpringApplication.run(JewelleryApplication.class, args);
	}
}
