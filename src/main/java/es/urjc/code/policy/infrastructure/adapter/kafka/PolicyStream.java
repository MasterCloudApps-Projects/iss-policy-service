package es.urjc.code.policy.infrastructure.adapter.kafka;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import es.urjc.code.policy.service.api.v1.events.EventType;
import es.urjc.code.policy.service.api.v1.events.PolicyEvent;
import es.urjc.code.policy.service.api.v1.events.dto.PolicyDto;

@Component
@EnableBinding(Source.class)
public class PolicyStream {

	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyStream.class);
	private final Source source;

	@Autowired
	public PolicyStream(Source source) {
		this.source = source;
	}

	public void policyTerminated(UUID id, PolicyDto policyDto) {
		PolicyEvent event = new PolicyEvent.Builder().withEventId(UUID.randomUUID().toString())
				.withEventTimestamp(System.currentTimeMillis()).withEventType(EventType.TERMINATED)
				.withPolicy(policyDto).withPolicyId(policyDto.getId()).build();
		sendToBus(id, event);
	}

	public void policyRegistred(UUID id, PolicyDto policyDto) {
		PolicyEvent event = new PolicyEvent.Builder().withEventId(UUID.randomUUID().toString())
				.withEventTimestamp(System.currentTimeMillis()).withEventType(EventType.REGISTERED)
				.withPolicy(policyDto).withPolicyId(policyDto.getId()).build();
		sendToBus(id, event);
	}

	private void sendToBus(UUID partitionKey, PolicyEvent event) {
		Message<PolicyEvent> message = MessageBuilder.withPayload(event)
				.setHeader("partitionKey", partitionKey)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build();

		source.output().send(message);
		LOGGER.info("\n---\nHeaders: {}\n\nPayload: {}\n---", message.getHeaders(), message.getPayload());
	}
}
