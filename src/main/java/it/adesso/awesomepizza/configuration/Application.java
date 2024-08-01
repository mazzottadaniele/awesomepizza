package it.adesso.awesomepizza.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import it.adesso.awesomepizza.util.Constants;

@SpringBootApplication
@EntityScan(basePackages = Constants.BASE_PACKAGE + ".data.persistence.entity")
@EnableJpaRepositories(basePackages = Constants.BASE_PACKAGE + ".data.persistence.repository")
@ComponentScan(basePackages = { Constants.BASE_PACKAGE })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}