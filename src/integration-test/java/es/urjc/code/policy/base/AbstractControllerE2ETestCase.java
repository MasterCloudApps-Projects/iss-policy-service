package es.urjc.code.policy.base;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.RestAssured;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerE2ETestCase extends AbstractContainerIntegrationTest {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUpBeforeEach() {
		RestAssured.port = this.port;
	}

	public enum Resources {
		V1_CREATE_OFFER_ENDPOINT("/api/v1/offers"), 
		V1_CREATE_POLICY_ENDPOINT("/api/v1/policies"),
		V1_TERMINATE_POLICIES_ENDPOINT("/api/v1/policies/terminate"),
		V1_GET_POLICY_BY_POLICY_NUMBER_ENDPOINT("/api/v1/policies/");

		private final String endpoint;

		Resources(String endpoint) {
			this.endpoint = endpoint;
		}

		public String build() {
			return endpoint;
		}
	}
}
