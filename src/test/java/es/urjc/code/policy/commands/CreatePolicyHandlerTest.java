package es.urjc.code.policy.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.policy.PolicyBuilder;
import es.urjc.code.policy.application.port.outgoing.LoadOfferPort;
import es.urjc.code.policy.application.port.outgoing.PublishPolicyStatePort;
import es.urjc.code.policy.application.port.outgoing.UpdatePolicyPort;
import es.urjc.code.policy.domain.AgentRef;
import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.domain.Person;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyCommand;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.CreatePolicyResult;
import es.urjc.code.policy.service.api.v1.commands.createpolicy.dto.PersonDto;

class CreatePolicyHandlerTest {
	
	private static final String TAXID = "111111116";
	private static final String LASTNAME = "Poirier";
	private static final String FIRSTNAME = "François";
	private static final String LOGIN = "admin";
	private static final String OFFER_NUMBER = "111";
	private LoadOfferPort loadOfferPort;
	private UpdatePolicyPort updatePolicyPort;
	private PublishPolicyStatePort publishPolicyStatePort;
	private CreatePolicyHandler sut;
	
	@BeforeEach
	public void setUp() {
		this.loadOfferPort = Mockito.mock(LoadOfferPort.class);
		this.updatePolicyPort = Mockito.mock(UpdatePolicyPort.class);
		this.publishPolicyStatePort = Mockito.mock(PublishPolicyStatePort.class);
		this.sut = new CreatePolicyHandler(loadOfferPort, updatePolicyPort, publishPolicyStatePort);
	}
	
	@Test
	void shouldBeCreatePolicy() {
		// given
		final CreatePolicyCommand cmd = getCreatePolicyCommand();
		final Policy policy = PolicyBuilder.build();
		final Offer offer = new Offer.Builder().build();
		final Person policyHolder = new Person.Builder().withFirstName(FIRSTNAME).withLastName(LASTNAME).withPesel(TAXID).build();
		final AgentRef agent = new AgentRef.Builder().withLogin(LOGIN).build();
		when(loadOfferPort.getOffer(OFFER_NUMBER)).thenReturn(offer);
		when(updatePolicyPort.createPolicy(offer, policyHolder, agent)).thenReturn(policy);
		// when
		final CreatePolicyResult result = this.sut.handle(cmd);
		// then
		verify(loadOfferPort).getOffer(OFFER_NUMBER);
		verify(updatePolicyPort).createPolicy(offer, policyHolder, agent);
		verify(publishPolicyStatePort).registered(policy);
		assertEquals(policy.getNumber(),result.getPolicyNumber());
	}
    
	private CreatePolicyCommand getCreatePolicyCommand() {
		return new CreatePolicyCommand.Builder()
				                      .withAgentLogin(LOGIN)
				                      .withOfferNumber(OFFER_NUMBER)
				                      .withPolicyHolder(new PersonDto.Builder().withFirstName(FIRSTNAME).withLastName(LASTNAME).withTaxId(TAXID).build())
				                      .build();
	}
}

