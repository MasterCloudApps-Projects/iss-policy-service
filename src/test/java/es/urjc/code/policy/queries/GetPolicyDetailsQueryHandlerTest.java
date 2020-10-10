package es.urjc.code.policy.queries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.policy.PolicyBuilder;
import es.urjc.code.policy.application.port.outgoing.LoadPolicyPort;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.queries.GetPolicyDetailsQueryHandler;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.GetPolicyDetailsQuery;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.GetPolicyDetailsQueryResult;

public class GetPolicyDetailsQueryHandlerTest {

	private LoadPolicyPort loadPolicyPort;
	private GetPolicyDetailsQueryHandler sut;
	
	@BeforeEach
	public void setUp() {
		this.loadPolicyPort = Mockito.mock(LoadPolicyPort.class);
		this.sut = new GetPolicyDetailsQueryHandler(loadPolicyPort);
	}
	
	@Test
	void shouldBeGetPolicyDetails() {
		// given
		final GetPolicyDetailsQuery query = getGetPolicyDetailsQuery();
		final Policy policy = PolicyBuilder.build();
		when(loadPolicyPort.getPolicy(PolicyBuilder.POLICY_NUMBER)).thenReturn(policy);
		// when
		final GetPolicyDetailsQueryResult result = this.sut.handle(query);
		// then
		verify(loadPolicyPort).getPolicy(policy.getNumber());
		assertEquals(policy.getNumber(),result.getPolicy().getNumber());
	}
	
	private GetPolicyDetailsQuery getGetPolicyDetailsQuery() {
		return new GetPolicyDetailsQuery.Builder().withNumber(PolicyBuilder.POLICY_NUMBER).build();
	}
}
