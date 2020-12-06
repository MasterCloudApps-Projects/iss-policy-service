package es.urjc.code.policy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import es.codeurjc.policy.command.bus.Bus;
import es.codeurjc.policy.command.bus.Registry;
import es.codeurjc.policy.command.bus.SpringBus;

@EnableJpaRepositories("es.urjc.code.policy.infrastructure.adapter.repository.jpa")
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class Application {
    
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    public Registry registry(ApplicationContext applicationContext) {
        return new Registry(applicationContext);
    }

    @Bean
    public Bus commandBus(Registry registry) {
        return new SpringBus(registry);
    }

}
