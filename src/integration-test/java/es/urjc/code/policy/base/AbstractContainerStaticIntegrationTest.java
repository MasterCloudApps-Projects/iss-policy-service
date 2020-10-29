package es.urjc.code.policy.base;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractContainerStaticIntegrationTest {
	
	protected static final PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:9.6.15").withDatabaseName("policy")
			.withUsername("postgres").withPassword("password");
	
	protected static KafkaContainer kafkaContainer = new KafkaContainer("5.5.1");

	@BeforeAll
	static void setUpAll() {
		if (!postgresContainer.isRunning()) {
		 postgresContainer.start();
		}
		if (!kafkaContainer.isRunning()) {
		 kafkaContainer.start();
		}
	}
	
	@Test
	void shouldBeRunning() {
		assertTrue(postgresContainer.isRunning());
		assertTrue(kafkaContainer.isRunning());
	}
	
	public static class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues
					.of("spring.datasource.driver-class-name=" + postgresContainer.getDriverClassName(),
							"spring.datasource.url=" + postgresContainer.getJdbcUrl(),
							"spring.datasource.username=" + postgresContainer.getUsername(),
							"spring.datasource.password=" + postgresContainer.getPassword(),
							"spring.kafka.bootstrap-servers", kafkaContainer.getBootstrapServers())
					.applyTo(configurableApplicationContext.getEnvironment());
		}
	}
}
