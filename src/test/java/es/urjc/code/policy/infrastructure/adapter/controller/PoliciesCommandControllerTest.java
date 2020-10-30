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
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyResult;
import es.urjc.code.policy.service.api.v1.commands.terminatepolicy.TerminatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.terminatepolicy.TerminatePolicyResult;

class PoliciesCommandControllerTest {

	private Bus bus;
	private PoliciesCommandController sut;
	
	@BeforeEach
	public void setUp() {
		this.bus = Mockito.mock(Bus.class);
		this.sut = new PoliciesCommandController(bus);
	}
	
	@Test
	void shouldBeInvokeCreatePoliccyUseCase() {
		// given
		final CreatePolicyCommand cmd =  getCreatePolicyCommand();
		final CreatePolicyResult result = getCreatePolicyResult();
		when(bus.executeCommand(cmd)).thenReturn(result);
		// when
		ResponseEntity<CreatePolicyResult> response = this.sut.create(cmd);
		// then
		verify(bus).executeCommand(cmd);
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		assertEquals(response.getBody(), result);
	}
	
	@Test
	void shouldBeInvokeTerminatePoliccyUseCase() {
		// given
		final TerminatePolicyCommand cmd =  getTerminatePolicyCommand();
		final TerminatePolicyResult result = getTerminatePolicyResult();
		when(bus.executeCommand(cmd)).thenReturn(result);
		// when
		ResponseEntity<TerminatePolicyResult> response = this.sut.terminate(cmd);
		// then
		verify(bus).executeCommand(cmd);
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		assertEquals(response.getBody(), result);
	}

	private CreatePolicyCommand getCreatePolicyCommand() {
		return new CreatePolicyCommand.Builder().build();
	}
	
	private CreatePolicyResult getCreatePolicyResult() {
		return new CreatePolicyResult.Builder().build();
	}
	
	private TerminatePolicyCommand getTerminatePolicyCommand() {
		return new TerminatePolicyCommand.Builder().build();
	}
	
	private TerminatePolicyResult getTerminatePolicyResult() {
		return new TerminatePolicyResult.Builder().build();
	}
	
}
