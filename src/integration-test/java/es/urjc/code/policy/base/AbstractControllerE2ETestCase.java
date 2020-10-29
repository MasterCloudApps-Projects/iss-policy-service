package es.urjc.code.policy.base;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerE2ETestCase extends AbstractContainerIntegrationTest {
	

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUpBeforeEach() {
		RestAssured.port = this.port;
	}

}
