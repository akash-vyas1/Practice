package akash.learning.codingChallenge_1.codingChallenge_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class CodingChallenge1Application {

	public static void main(String[] args) {
		SpringApplication.run(CodingChallenge1Application.class, args);
	}

}
