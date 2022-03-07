package ro.robert.duckling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DucklingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DucklingApplication.class, args);
	}

}
