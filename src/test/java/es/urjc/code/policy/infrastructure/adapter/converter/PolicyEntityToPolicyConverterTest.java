package es.urjc.code.policy.infrastructure.adapter.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.AgentRefEmbeddable;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.PolicyEntity;

public class PolicyEntityToPolicyConverterTest {

	private static final String ADMIN_AGENT = "admin";
	private static final UUID ID = UUID.randomUUID();
	public static final String POLICY_NUMBER = "P1212121";
	private PolicyVersionEntityToPolicyVersionConverter policyVersionEntityToPolicyVersionConverter;
	private PolicyEntityToPolicyConverter sut;
	
	@BeforeEach
	public void setUp() {
		this.policyVersionEntityToPolicyVersionConverter = Mockito.mock(PolicyVersionEntityToPolicyVersionConverter.class);
		this.sut = new PolicyEntityToPolicyConverter(policyVersionEntityToPolicyVersionConverter);
	}
	
	@Test
	public void shouldBeConvert() {
		// given
		final PolicyEntity entity = getPolicyEntity();
		// when
		final Policy response = this.sut.convert(entity);
		// then
		assertEquals(ID, response.getId());
		assertEquals(POLICY_NUMBER,response.getNumber());
		assertEquals(ADMIN_AGENT,response.getAgent().getLogin());
	}
	
	private PolicyEntity getPolicyEntity() {
		AgentRefEmbeddable agent = new AgentRefEmbeddable.Builder().withLogin(ADMIN_AGENT).build();
		return new PolicyEntity.Builder()
				               .withAgent(agent)
				               .withId(ID)
				               .withNumber(POLICY_NUMBER)
				               .withVersions(Collections.EMPTY_SET)
				               .build();
	}
}
