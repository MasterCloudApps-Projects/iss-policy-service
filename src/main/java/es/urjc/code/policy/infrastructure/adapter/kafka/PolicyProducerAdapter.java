package es.urjc.code.policy.infrastructure.adapter.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;

import es.urjc.code.policy.application.port.outgoing.PolicyEventProducerPort;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.domain.PolicyVersion;
import es.urjc.code.policy.service.api.v1.events.PolicyRegisteredEvent;
import es.urjc.code.policy.service.api.v1.events.PolicyTerminatedEvent;
import es.urjc.code.policy.service.api.v1.events.dto.PolicyDto;

@Service
@Transactional
public class PolicyProducerAdapter implements PolicyEventProducerPort {

	private final PoliciesStreams policiesStreams;
	
	@Autowired
	public PolicyProducerAdapter(PoliciesStreams policiesStreams){
		this.policiesStreams = policiesStreams;
	}
	
	@Override
	public boolean terminated(Policy policy) {
		MessageChannel messageChannel = policiesStreams.outboundTerminated();
		return messageChannel.send(MessageBuilder
                .withPayload(createTerminatedEvent(policy))
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
	}

	@Override
	public boolean registered(Policy policy) {
		MessageChannel messageChannel = policiesStreams.outboundRegistred();
		return messageChannel.send(MessageBuilder
                .withPayload(createRegisteredEvent(policy))
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
	}
	
    private PolicyTerminatedEvent createTerminatedEvent(Policy policy) {
    	PolicyDto dto = createPolicyDto(policy);
        return new PolicyTerminatedEvent.Builder().withPolicy(dto).build();
    }
    
    private PolicyRegisteredEvent createRegisteredEvent(Policy policy) {
    	PolicyDto dto = createPolicyDto(policy);
    	return new PolicyRegisteredEvent.Builder().withPolicy(dto).build();
    }

	private PolicyDto createPolicyDto(Policy policy) {
		PolicyVersion lastVersion = policy.versions().lastVersion();
		return new PolicyDto.Builder()
    			                     .withNumber(policy.getNumber())
    			                     .withFrom(lastVersion.getVersionValidityPeriod().getFrom())
    			                     .withTo(lastVersion.getVersionValidityPeriod().getTo())
    			                     .withPolicyHolder(lastVersion.getPolicyHolder().getFullName())
    			                     .withProductCode(lastVersion.getProductCode())
    			                     .withTotalPremium(lastVersion.getTotalPremiumAmount())
    			                     .withAgentLogin(policy.getAgent().getLogin())
    			                     .build();
	}

}
