package es.urjc.code.policy.infrastructure.adapter.kafka;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

import es.urjc.code.policy.service.api.v1.events.dto.PolicyDto;

public class PolicyStreamTest {

	private static final String POLICY_HOLDER = "François Poirier";
	private static final String PRODUCT_CODE = "CAR";
	private static final String ADMIN_AGENT = "admin";
	public static final String POLICY_NUMBER = "P1212121";
	private static final UUID POLICY_ID = UUID.randomUUID();
	private Source source;
	private PolicyStream sut;
	
	@BeforeEach
	public void setUp() {
		this.source = Mockito.mock(Source.class);
		this.sut = new PolicyStream(source);
	}
	
	@Test
	void shouldBeSendEventTerminated() {
		// given
	    final PolicyDto policyDto = getPolicyDto();
	    final MessageChannel messageChannel = Mockito.mock(MessageChannel.class);
	    when(source.output()).thenReturn(messageChannel);
	    // when
	    this.sut.policyTerminated(policyDto.getId(), policyDto);
	    // then
	    verify(source).output();
	    verify(messageChannel).send(any());
	}
	
	@Test
	void shouldBeSendEventRegistered() {
		// given
	    final PolicyDto policyDto = getPolicyDto();
	    final MessageChannel messageChannel = Mockito.mock(MessageChannel.class);
	    when(source.output()).thenReturn(messageChannel);
	    // when
	    this.sut.policyRegistred(policyDto.getId(), policyDto);
	    // then
	    verify(source).output();
	    verify(messageChannel).send(any());		
	}
	
	private PolicyDto getPolicyDto() {
		return new PolicyDto.Builder().withId(POLICY_ID)
				                      .withAgentLogin(ADMIN_AGENT)
				                      .withFrom(LocalDate.of(2018, 1, 1))
				                      .withNumber(POLICY_NUMBER)
				                      .withProductCode(PRODUCT_CODE)
				                      .withPolicyHolder(POLICY_HOLDER)
				                      .withTo(LocalDate.of(2018, 12, 31))
				                      .build();
	}
}
