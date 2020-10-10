package es.urjc.code.policy.application.port.outgoing;

import es.urjc.code.policy.domain.AgentRef;
import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.domain.Person;
import es.urjc.code.policy.domain.Policy;

public interface UpdatePolicyPort {

	 public Policy updateTerminateState(String policyNumber);
	 
	 public Policy createPolicy(Offer offer, Person policyHolder, AgentRef agent); 
}
