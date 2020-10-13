package es.urjc.code.policy.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.policy.PolicyBuilder;
import es.urjc.code.policy.application.port.outgoing.PolicyEventProducerPort;
import es.urjc.code.policy.application.port.outgoing.UpdatePolicyPort;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.service.api.v1.commands.terminatepolicy.TerminatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.terminatepolicy.TerminatePolicyResult;

class TerminatePolicyHandlerTest {

	private UpdatePolicyPort updatePolicyPort;
	private PolicyEventProducerPort policyEventProducerPort;
	private TerminatePolicyHandler sut;
	
	@BeforeEach
	public void setUp() {
		this.updatePolicyPort = Mockito.mock(UpdatePolicyPort.class);
		this.policyEventProducerPort = Mockito.mock(PolicyEventProducerPort.class);
		this.sut = new TerminatePolicyHandler(updatePolicyPort, policyEventProducerPort);
	}
	
	@Test
	void shouldBeTerminatePolicy() {
		// given
		final TerminatePolicyCommand cmd = getTerminatePolicyCommand();
		final Policy policy = PolicyBuilder.build();
		when(updatePolicyPort.updateTerminateState(PolicyBuilder.POLICY_NUMBER)).thenReturn(policy);
		// when
		final TerminatePolicyResult result = this.sut.handle(cmd);
		// then
		verify(updatePolicyPort).updateTerminateState(PolicyBuilder.POLICY_NUMBER);
		verify(policyEventProducerPort).terminated(policy);
		assertEquals(policy.getNumber(),result.getPolicyNumber());
	}
	
	public TerminatePolicyCommand getTerminatePolicyCommand() {
		return new TerminatePolicyCommand.Builder().withPolicyNumber(PolicyBuilder.POLICY_NUMBER).build();
	}
}
