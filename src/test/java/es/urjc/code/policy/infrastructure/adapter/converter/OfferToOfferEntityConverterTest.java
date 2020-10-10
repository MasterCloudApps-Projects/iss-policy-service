package es.urjc.code.policy.infrastructure.adapter.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.domain.OfferStatus;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.OfferEntity;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.OfferStatusEnum;

public class OfferToOfferEntityConverterTest {

	private static final String OFFER_NUMBER = "111";
	private static final String CODE_CAR = "CAR";
	private OfferToOfferEntityConverter sut;
	
	@BeforeEach
	public void setUp() {
		this.sut = new OfferToOfferEntityConverter();
	}
	
	@Test
	public void shouldBeConvert() {
		// given
		final Offer offer = getOffer();
		// when
		final OfferEntity response = this.sut.convert(offer);
		// then
		assertEquals(OFFER_NUMBER,response.getNumber());
		assertEquals(OfferStatusEnum.NEW,response.getStatus());
	}
	
	private Offer getOffer() {
		return new Offer.Builder()
				              .withAnswers(Collections.singletonMap("NUM_OF_CLAIM", "1"))
				              .withCreationDate(LocalDate.now())
				              .withId(UUID.randomUUID())
				              .withNumber(OFFER_NUMBER)
				              .withPolicyFrom(LocalDate.of(2017, 4, 16))
				              .withPolicyTo(LocalDate.of(2018, 4, 15))
				              .withCoversPrices(Collections.singletonMap("C1", BigDecimal.TEN))
				              .withProductCode(CODE_CAR)
				              .withStatus(OfferStatus.NEW)
				              .withTotalPrice(new BigDecimal(10000))
				              .build();
	}
}
