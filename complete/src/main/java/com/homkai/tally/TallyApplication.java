package com.homkai.tally;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TallyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TallyApplication.class, args);
	}
}
