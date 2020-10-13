package es.urjc.code.policy.infrastructure.adapter.kafka;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PoliciesStreams {

	final String OUTPUT_POLICY_REGISTRED = "policy-registered-out";
	final String OUTPUT_POLICY_TERMINATED = "policy-terminated-out";
	
	@Output(OUTPUT_POLICY_TERMINATED)
	MessageChannel outboundTerminated();
	
	@Output(OUTPUT_POLICY_REGISTRED)
	MessageChannel outboundRegistred();

}
