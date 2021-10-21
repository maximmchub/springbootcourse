package ua.raif.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class ConferenceManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConferenceManagerApplication.class, args);
	}

}
