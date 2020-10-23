package es.urjc.code.policy.infrastructure.adapter.kafka;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.MessageChannel;

import es.urjc.code.policy.PolicyBuilder;
import es.urjc.code.policy.domain.Policy;

class PolicyEventProducerAdapterTest {

	private PoliciesStreams policiesStreams; 
	private PolicyEventProducerAdapter sut;
	
	@BeforeEach
	public void setUp() {
		this.policiesStreams = Mockito.mock(PoliciesStreams.class);
		this.sut = new PolicyEventProducerAdapter(policiesStreams);
	}
	
	@Test
	void testTerminated() {
	   // given
       final Policy policy = PolicyBuilder.build();
       final MessageChannel messageChannel = Mockito.mock(MessageChannel.class);
       when(policiesStreams.outboundTerminated()).thenReturn(messageChannel);
       // when
       this.sut.terminated(policy);
       // then
       verify(policiesStreams).outboundTerminated();
       verify(messageChannel).send(any());
	}
	
	@Test
	void testRegistered() {
		final Policy policy = PolicyBuilder.build();
	       final MessageChannel messageChannel = Mockito.mock(MessageChannel.class);
	       when(policiesStreams.outboundRegistred()).thenReturn(messageChannel);
	       // when
	       this.sut.registered(policy);
	       // then
	       verify(policiesStreams).outboundRegistred();
	       verify(messageChannel).send(any());
	}
	
}
