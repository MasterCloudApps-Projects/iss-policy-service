package es.urjc.code.policy.infrastructure.adapter.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.urjc.code.policy.application.port.outgoing.PolicyEventProducerPort;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.domain.PolicyVersion;
import es.urjc.code.policy.service.api.v1.events.dto.PolicyDto;

@Service
@Transactional
public class PolicyEventProducerAdapter implements PolicyEventProducerPort {

	private final PolicyStream policyStream;
	
	@Autowired
	public PolicyEventProducerAdapter(PolicyStream policyStream){
		this.policyStream = policyStream;
	}
	
	@Override
	public void terminated(Policy policy) {
		policyStream.policyTerminated(policy.getId(), createPolicyDto(policy));
	}

	@Override
	public void registered(Policy policy) {
		policyStream.policyRegistred(policy.getId(), createPolicyDto(policy));
	}

	private PolicyDto createPolicyDto(Policy policy) {
		PolicyVersion lastVersion = policy.versions().lastVersion();
		return new PolicyDto.Builder()
				                     .withId(policy.getId()) 
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
