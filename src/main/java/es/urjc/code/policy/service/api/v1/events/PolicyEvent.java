package es.urjc.code.policy.service.api.v1.events;

import javax.validation.constraints.NotNull;

import es.urjc.code.policy.service.api.v1.events.dto.PolicyDto;

public class PolicyEvent {
	
	@NotNull
    private PolicyDto policy;

	public PolicyDto getPolicy() {
		return policy;
	}

	public void setPolicy(PolicyDto policy) {
		this.policy = policy;
	}
}
