package es.urjc.code.policy.infrastructure.adapter.kafka;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import es.urjc.code.policy.Application;
import es.urjc.code.policy.base.AbstractContainerIntegrationTest;
import es.urjc.code.policy.service.api.v1.events.dto.PolicyDto;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext
@ActiveProfiles("test")
class PolicyStreamIT extends AbstractContainerIntegrationTest {

	private static final String POLICY_HOLDER = "François Poirier";
	private static final String PRODUCT_CODE = "CAR";
	private static final String ADMIN_AGENT = "admin";
	public static final String POLICY_NUMBER = "P1212121";
	private static final UUID POLICY_ID = UUID.randomUUID();
	
	@Autowired
	private Source channels;

	@Autowired
	private MessageCollector collector;
	
	private PolicyStream sut;
	
	@BeforeEach
	public void setUp() {
	  this.sut = new PolicyStream(channels);	
	}
	
	@Test
	void shouldPropagatePolicyEvents ()
	{
		// given
		final PolicyDto policyDto = getPolicyDto();
		final BlockingQueue<Message<?>> messages = this.collector.forChannel(channels.output());
		// when
		this.sut.policyRegistred(policyDto.getId(), policyDto);
		// then
		assertThat(messages, hasSize(1));
		List<String> sList = messages.stream().map(Message::toString).collect(Collectors.toList());
		String jsonString = sList.get(0);
		assertTrue(jsonString.contains(POLICY_HOLDER));
		assertTrue(jsonString.contains(PRODUCT_CODE));
		assertTrue(jsonString.contains(ADMIN_AGENT));
		assertTrue(jsonString.contains(POLICY_NUMBER));
	}
	
	private PolicyDto getPolicyDto() {
		return new PolicyDto.Builder().withId(POLICY_ID)
				                      .withAgentLogin(ADMIN_AGENT)
				                      .withFrom(LocalDate.of(2018, 1, 1))
				                      .withNumber(POLICY_NUMBER)
				                      .withProductCode(PRODUCT_CODE)
				                      .withPolicyHolder(POLICY_HOLDER)
				                      .withTo(LocalDate.of(2018, 12, 31))
				                      .withTotalPremium( new BigDecimal(1000))
				                      .build();
	}
}
