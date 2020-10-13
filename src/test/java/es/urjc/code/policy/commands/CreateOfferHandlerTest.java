package es.urjc.code.policy.commands;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.policy.application.port.outgoing.PricingClientPort;
import es.urjc.code.policy.application.port.outgoing.UpdateOfferPort;
import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferCommand;
import es.urjc.code.policy.service.api.v1.commands.createoffer.CreateOfferResult;
import es.urjc.code.policy.service.api.v1.commands.createoffer.dto.QuestionAnswer;

class CreateOfferHandlerTest {

	private static final String CODE_CAR = "CAR";
	private static final String OFFER_NUMBER = "111";
	private PricingClientPort pricingClientPort;
	private UpdateOfferPort updateOfferPort;
	private CreateOfferHandler sut;
	
	@BeforeEach
	public void setUp() {
		this.pricingClientPort = Mockito.mock(PricingClientPort.class);
		this.updateOfferPort = Mockito.mock(UpdateOfferPort.class);
		this.sut = new CreateOfferHandler(pricingClientPort, updateOfferPort);
	}
	
	@Test
	void shouldBeCreateOffer() {
		// given
		final CreateOfferCommand cmd = getCreateOfferCommand();
		when(updateOfferPort.createOffer(any(), any())).thenReturn(new Offer.Builder().withNumber(OFFER_NUMBER).build());
		when(pricingClientPort.calculatePrice(any())).thenReturn(any());
		//when
		final CreateOfferResult result = this.sut.handle(cmd);
		// then
		verify(pricingClientPort).calculatePrice(any());
		verify(updateOfferPort).createOffer(any(), any());
		assertNotNull(result);
	}
	
	private CreateOfferCommand getCreateOfferCommand() {
		ArrayList<QuestionAnswer> questionAnswers = new ArrayList();
		questionAnswers.add(new QuestionAnswer("NUM_OF_CLAIM", 1));
		return new CreateOfferCommand.Builder()
				                     .withProductCode(CODE_CAR)
				                     .withPolicyFrom(LocalDate.of(2017, 4, 16))
				                     .withPolicyTo(LocalDate.of(2018, 4, 15))
				                     .withAnswers(questionAnswers)
				                     .withSelectedCovers(Collections.singletonList("C1"))
				                     .build();
	}
}
