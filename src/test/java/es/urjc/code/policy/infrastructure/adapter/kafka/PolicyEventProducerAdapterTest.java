package es.urjc.code.policy.infrastructure.adapter.kafka;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.policy.PolicyBuilder;
import es.urjc.code.policy.domain.Policy;

class PolicyEventProducerAdapterTest {

	private PolicyStream policyStream; 
	private PolicyEventProducerAdapter sut;
	
	@BeforeEach
	public void setUp() {
		this.policyStream = Mockito.mock(PolicyStream.class);
		this.sut = new PolicyEventProducerAdapter(policyStream);
	}
	
	@Test
	void testTerminated() {
	   // given
       final Policy policy = PolicyBuilder.build();
       doNothing().when(policyStream).policyTerminated(any(),any());
       // when
       this.sut.terminated(policy);
       // then
       verify(policyStream).policyTerminated(any(),any());
	}
	
	@Test
	void testRegistered() {
		final Policy policy = PolicyBuilder.build();
		doNothing().when(policyStream).policyTerminated(any(),any());
	    // when
	    this.sut.registered(policy);
	    // then
	    verify(policyStream).policyRegistred(any(),any());
	}
	
}
