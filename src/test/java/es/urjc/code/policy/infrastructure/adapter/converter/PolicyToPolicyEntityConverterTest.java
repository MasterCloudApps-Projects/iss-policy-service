package es.urjc.code.policy.infrastructure.adapter.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.policy.domain.AgentRef;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.PolicyEntity;

class PolicyToPolicyEntityConverterTest {
	
	
	private static final String ADMIN_AGENT = "admin";
	private static final UUID ID = UUID.randomUUID();
	public static final String POLICY_NUMBER = "P1212121";
	
	private PolicyVersionToPolicyVersionEntityConverter policyVersionToPolicyVersionEntityConverter;
	private PolicyToPolicyEntityConverter sut;
	
	@BeforeEach
	public void setUp() {
		this.policyVersionToPolicyVersionEntityConverter = Mockito.mock(PolicyVersionToPolicyVersionEntityConverter.class);
		this.sut = new PolicyToPolicyEntityConverter(policyVersionToPolicyVersionEntityConverter);
	}
	
	@Test
	void shouldBeConvert() {
		// given
		final Policy policy = getPolicy();
		// when
		final PolicyEntity response = this.sut.convert(policy);
		// then
		assertEquals(ID, response.getId());
		assertEquals(POLICY_NUMBER,response.getNumber());
		assertEquals(ADMIN_AGENT,response.getAgent().getLogin());
	}
	
	private Policy getPolicy() {
		AgentRef agent = new AgentRef.Builder().withLogin(ADMIN_AGENT).build();
		return new Policy.Builder()
				               .withAgent(agent)
				               .withId(ID)
				               .withNumber(POLICY_NUMBER)
				               .withVersions(Collections.EMPTY_SET)
				               .build();
	}
}
