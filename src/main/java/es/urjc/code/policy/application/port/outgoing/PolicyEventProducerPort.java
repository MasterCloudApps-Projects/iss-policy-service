package es.urjc.code.policy.application.port.outgoing;

import es.urjc.code.policy.domain.Policy;

public interface PolicyEventProducerPort {

	public void terminated(Policy policy);
	public void registered(Policy policy);
}
