package es.urjc.code.policy.infrastructure.adapter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.codeurjc.policy.command.bus.Bus;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.GetPolicyDetailsQuery;
import es.urjc.code.policy.service.api.v1.queries.getpolicydetails.GetPolicyDetailsQueryResult;

class PoliciesQueryControllerTest {

	public static final String POLICY_NUMBER = "P1212121";
	private Bus bus;
	private PoliciesQueryController sut;
	
	@BeforeEach
	public void setUp() {
		this.bus = Mockito.mock(Bus.class);
		this.sut = new PoliciesQueryController(bus);
	}
	
	@Test
	void shouldBeInvokeGetPolicyDetails() {
		// given
		final GetPolicyDetailsQuery query =  getGetPolicyDetailsQuery();
		final GetPolicyDetailsQueryResult result = getGetPolicyDetailsQueryResult();
		when(bus.executeQuery(query)).thenReturn(result);
		// when
		ResponseEntity<GetPolicyDetailsQueryResult>  response = this.sut.get(POLICY_NUMBER);
		// then
		verify(bus).executeQuery(query);
		assertEquals(HttpStatus.OK,response.getStatusCode());
		assertEquals(response.getBody(), result);
	}

	private GetPolicyDetailsQueryResult getGetPolicyDetailsQueryResult() {
		return new GetPolicyDetailsQueryResult.Builder().build();
	}

	private GetPolicyDetailsQuery getGetPolicyDetailsQuery() {
		return new GetPolicyDetailsQuery.Builder().withNumber(POLICY_NUMBER).build();
	}
	
}
