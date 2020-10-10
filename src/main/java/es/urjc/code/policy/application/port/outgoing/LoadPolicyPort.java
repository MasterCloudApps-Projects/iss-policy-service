package es.urjc.code.policy.application.port.outgoing;

import es.urjc.code.policy.domain.Policy;

public interface LoadPolicyPort {
  public Policy getPolicy(String policyNumber);
}
