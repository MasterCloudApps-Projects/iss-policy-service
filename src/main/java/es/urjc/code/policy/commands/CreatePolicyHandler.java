package es.urjc.code.policy.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.urjc.code.policy.application.port.incoming.CreatePolicyUseCase;
import es.urjc.code.policy.application.port.outgoing.LoadOfferPort;
import es.urjc.code.policy.application.port.outgoing.PolicyEventProducerPort;
import es.urjc.code.policy.application.port.outgoing.UpdatePolicyPort;
import es.urjc.code.policy.domain.AgentRef;
import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.domain.Person;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyResult;

@Component
public class CreatePolicyHandler implements CreatePolicyUseCase {

	private final LoadOfferPort loadOfferPort;
	private final UpdatePolicyPort updatePolicyPort;
	private final PolicyEventProducerPort policyEventProducerPort;
	
	@Autowired
	public CreatePolicyHandler (LoadOfferPort loadOfferPort, UpdatePolicyPort updatePolicyPort, PolicyEventProducerPort policyEventProducerPort) {
		this.loadOfferPort = loadOfferPort;
		this.updatePolicyPort = updatePolicyPort;
		this.policyEventProducerPort = policyEventProducerPort;
	}
	
	
    @Transactional
	@Override
	public CreatePolicyResult handle(CreatePolicyCommand command) {
        
        final Person policyHolder = new Person.Builder()
        		                              .withFirstName(command.getPolicyHolder().getFirstName())
        		                              .withLastName(command.getPolicyHolder().getLastName())
        		                              .withPesel(command.getPolicyHolder().getTaxId())
        		                              .build(); 
        final AgentRef agent = new AgentRef.Builder().withLogin(command.getAgentLogin()).build();
        final Offer offer = loadOfferPort.getOffer(command.getOfferNumber());
    	final Policy policy = updatePolicyPort.createPolicy(offer, policyHolder, agent);
    	policyEventProducerPort.registered(policy);
    	return new CreatePolicyResult.Builder().withPolicyNumber(policy.getNumber()).build();
	}

}
