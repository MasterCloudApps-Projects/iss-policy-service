package es.urjc.code.policy.infrastructure.adapter.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.testcontainers.junit.jupiter.Testcontainers;

import es.urjc.code.policy.base.AbstractControllerE2ETestCase;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class OffersCommandControllerE2ETestCase extends AbstractControllerE2ETestCase {


}
