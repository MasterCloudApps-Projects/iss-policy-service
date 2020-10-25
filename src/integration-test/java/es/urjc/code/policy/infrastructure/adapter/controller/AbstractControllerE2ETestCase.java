package es.urjc.code.policy.infrastructure.adapter.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerE2ETestCase {
	
	@Container
	public static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:9.6.15").withDatabaseName("pricing")
			.withUsername("postgres").withPassword("password");

	@DynamicPropertySource
	static void postgresqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
		registry.add("spring.datasource.password", postgresContainer::getPassword);
		registry.add("spring.datasource.username", postgresContainer::getUsername);
	}

	@LocalServerPort
	private int port;

	@BeforeAll
	static void setUpAll() {
		if (!postgresContainer.isRunning()) {
			 postgresContainer.start();
		}
	}
	
	@BeforeEach
	void setUpBeforeEach() {
		RestAssured.port = this.port;
	}

}
