package es.urjc.code.policy.infrastructure.adapter.converter;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.domain.PolicyVersion;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.AgentRefEmbeddable;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.PolicyEntity;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.PolicyVersionEntity;

@Component
public class PolicyToPolicyEntityConverter implements Converter<Policy, PolicyEntity> {

	private final PolicyVersionToPolicyVersionEntityConverter policyVersionToPolicyVersionEntityConverter;
	
	@Autowired
	public PolicyToPolicyEntityConverter(PolicyVersionToPolicyVersionEntityConverter policyVersionToPolicyVersionEntityConverter) {
		this.policyVersionToPolicyVersionEntityConverter = policyVersionToPolicyVersionEntityConverter;
	}

	@Override
	public PolicyEntity convert(Policy source) {
		final AgentRefEmbeddable agenRef = new AgentRefEmbeddable.Builder().withLogin(source.getAgent().getLogin()).build();  
		return new PolicyEntity.Builder()
				         .withId(source.getId())
				         .withNumber(source.getNumber())
				         .withAgent(agenRef)
				         .withVersions(source.getVersions().stream().map(v-> toPolicyVersionEntity(v)).collect(Collectors.toSet()))
				         .build();
	}

	private PolicyVersionEntity toPolicyVersionEntity(PolicyVersion version) {
		return policyVersionToPolicyVersionEntityConverter.convert(version);
	}
}
