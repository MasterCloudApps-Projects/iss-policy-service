package es.urjc.code.policy.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.codeurjc.policy.command.bus.CommandHandler;
import es.urjc.code.policy.application.port.outgoing.PolicyEventProducerPort;
import es.urjc.code.policy.application.port.outgoing.UpdatePolicyPort;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.service.api.v1.commands.terminatepolicy.TerminatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.terminatepolicy.TerminatePolicyResult;

@Component
@Transactional
public class TerminatePolicyHandler implements CommandHandler<TerminatePolicyResult,TerminatePolicyCommand> {

	private final UpdatePolicyPort updatePolicyPort;
	private final PolicyEventProducerPort policyEventProducerPort;
	
	@Autowired
	public TerminatePolicyHandler (UpdatePolicyPort updatePolicyPort, PolicyEventProducerPort policyEventProducerPort) {
		this.updatePolicyPort = updatePolicyPort;
		this.policyEventProducerPort = policyEventProducerPort;
	}
	
	@Override
	public TerminatePolicyResult handle(TerminatePolicyCommand command) {
    	final Policy policy = updatePolicyPort.updateTerminateState(command.getPolicyNumber());
    	policyEventProducerPort.terminated(policy);   	
		return new TerminatePolicyResult.Builder().withPolicyNumber(policy.getNumber()).build();
	}

}
