package es.urjc.code.policy.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.codeurjc.policy.command.bus.CommandHandler;
import es.urjc.code.policy.application.port.outgoing.LoadOfferPort;
import es.urjc.code.policy.application.port.outgoing.PublishPolicyStatePort;
import es.urjc.code.policy.application.port.outgoing.UpdatePolicyPort;
import es.urjc.code.policy.domain.AgentRef;
import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.domain.Person;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyResult;

@Component
public class CreatePolicyHandler implements CommandHandler<CreatePolicyResult,CreatePolicyCommand> {

	private final LoadOfferPort loadOfferPort;
	private final UpdatePolicyPort updatePolicyPort;
	private final PublishPolicyStatePort publishPolicyStatePort;
	
	@Autowired
	public CreatePolicyHandler (LoadOfferPort loadOfferPort, UpdatePolicyPort updatePolicyPort, PublishPolicyStatePort publishPolicyStatePort) {
		this.loadOfferPort = loadOfferPort;
		this.updatePolicyPort = updatePolicyPort;
		this.publishPolicyStatePort = publishPolicyStatePort;
	}
	
	
    @Transactional
	@Override
	public CreatePolicyResult handle(CreatePolicyCommand cmd) {
        
        final Person policyHolder = new Person.Builder()
        		                              .withFirstName(cmd.getPolicyHolder().getFirstName())
        		                              .withLastName(cmd.getPolicyHolder().getLastName())
        		                              .withPesel(cmd.getPolicyHolder().getTaxId())
        		                              .build(); 
        final AgentRef agent = new AgentRef.Builder().withLogin(cmd.getAgentLogin()).build();
        final Offer offer = loadOfferPort.getOffer(cmd.getOfferNumber());
    	final Policy policy = updatePolicyPort.createPolicy(offer, policyHolder, agent);
    	publishPolicyStatePort.registered(policy);
    	return new CreatePolicyResult.Builder().withPolicyNumber(policy.getNumber()).build();
	}

}
