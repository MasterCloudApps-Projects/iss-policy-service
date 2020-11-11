package es.urjc.code.policy.infrastructure.adapter.repository.jpa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import es.urjc.code.policy.base.AbstractContainerBaseTest;
import es.urjc.code.policy.domain.AgentRef;
import es.urjc.code.policy.domain.Policy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(initializers = { AbstractContainerBaseTest.PropertiesInitializer.class })
@ActiveProfiles("test")
class PolicyJpaRepositoryIT extends AbstractContainerBaseTest {
	
	private static final String ADMIN_AGENT = "admin";
	public static final String POLICY_NUMBER = "P1212121";
	private static final String POLICY_NUMER_NOT_EXIST = "NOT_EXIST";
	
	@Autowired
	private PolicyJpaRepository policyJpaRepository;

	private Policy entity;
	
	@BeforeEach
	public void setUp() {
		AgentRef agent = new AgentRef.Builder().withLogin(ADMIN_AGENT).build();
		this.entity = new Policy.Builder()
	               .withAgent(agent)
	               .withNumber(POLICY_NUMBER)
	               .build();
		policyJpaRepository.save(entity);
	}
	
	@Test
	void testWhenFindByPolicyNumberThenReturnPolicy() {	
		final var p = policyJpaRepository.findByNumber(POLICY_NUMBER);
		assertTrue(p.isPresent());
	}

	@Test
	void testWhenFindByPolicyNumberThenNotReturnPolicy() {
		final var p = policyJpaRepository.findByNumber(POLICY_NUMER_NOT_EXIST);
		assertTrue(!p.isPresent());
	}
}
