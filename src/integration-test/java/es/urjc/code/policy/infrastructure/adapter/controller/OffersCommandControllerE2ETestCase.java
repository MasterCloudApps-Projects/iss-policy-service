package es.urjc.code.policy.infrastructure.adapter.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import es.urjc.code.policy.base.AbstractControllerE2ETestCase;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OffersCommandControllerE2ETestCase extends AbstractControllerE2ETestCase {


}
