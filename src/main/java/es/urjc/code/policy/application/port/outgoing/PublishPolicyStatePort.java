package es.urjc.code.policy.application.port.outgoing;

import es.urjc.code.policy.domain.Policy;

public interface PublishPolicyStatePort {

	public boolean terminated(Policy policy);
	public boolean registered(Policy policy);
}
