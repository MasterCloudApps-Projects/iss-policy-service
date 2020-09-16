package es.urjc.code.policy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("es.urjc.code.policy.infrastructure.adapter.repository.jpa")
@SpringBootApplication
public class Application {
    
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
