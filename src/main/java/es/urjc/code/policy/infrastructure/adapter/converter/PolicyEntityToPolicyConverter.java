package es.urjc.code.policy.infrastructure.adapter.converter;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import es.urjc.code.policy.domain.AgentRef;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.domain.PolicyVersion;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.PolicyEntity;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.PolicyVersionEntity;

@Component
public class PolicyEntityToPolicyConverter implements Converter<PolicyEntity, Policy> {

	private final PolicyVersionEntityToPolicyVersionConverter policyVersionEntityToPolicyVersionConverter;
	
	@Autowired
	public PolicyEntityToPolicyConverter(PolicyVersionEntityToPolicyVersionConverter policyVersionEntityToPolicyVersionConverter) {
		this.policyVersionEntityToPolicyVersionConverter = policyVersionEntityToPolicyVersionConverter;
	}
	
	@Override
	public Policy convert(PolicyEntity source) {
		final AgentRef agenRef = new AgentRef.Builder().withLogin(source.getAgent().getLogin()).build();  
		return new Policy.Builder()
				         .withId(source.getId())
				         .withNumber(source.getNumber())
				         .withAgent(agenRef)
				         .withVersions(source.getVersions().stream().map(v-> toPolicyVersion(v)).collect(Collectors.toSet()))
				         .build();
	}

	private PolicyVersion toPolicyVersion(PolicyVersionEntity entity) {
		return policyVersionEntityToPolicyVersionConverter.convert(entity);
	}
}
