package es.urjc.code.policy.infrastructure.adapter.repository;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.exception.BusinessException;
import es.urjc.code.policy.infrastructure.adapter.repository.jpa.OfferJpaRepository;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceCommand;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceResult;

class OfferRepositoryAdapterTest {

	private static final LocalDate EXPIRED_DATE = LocalDate.now().minusDays(40);
	private static final LocalDate NOT_EXPIRED_DATE = LocalDate.now().minusDays(29);
	private static final String OFFER_NUMBER = "111";
	private OfferJpaRepository offerJpaRepository;
	private OfferRepositoryAdapter sut;
	
	@BeforeEach
	public void setUp() {
		this.offerJpaRepository = Mockito.mock(OfferJpaRepository.class);
		this.sut = new OfferRepositoryAdapter(offerJpaRepository);
	}
	
	@Test
	void shouldBeGetOfferExpired() {
		// given
		when(offerJpaRepository.getByNumber(OFFER_NUMBER)).thenReturn(getExpiredOffer());
		// when
		assertThrows(BusinessException.class, () -> {
			this.sut.getOffer(OFFER_NUMBER);
	    });
		// then
		verify(offerJpaRepository).getByNumber(OFFER_NUMBER);
	}

	@Test
	void shouldBeGetOfferNotExpired() {
		// given
		when(offerJpaRepository.getByNumber(OFFER_NUMBER)).thenReturn(getNotExpiredOffer());
		// when
		this.sut.getOffer(OFFER_NUMBER);
		// then
		verify(offerJpaRepository).getByNumber(OFFER_NUMBER);
	}

	@Test
	void shouldBeCreateOffer() {
		// given
		when(offerJpaRepository.save(getOffer())).thenReturn(getOffer());
		// when
		this.sut.createOffer(getCalculatePriceCommand(), getCalculatePriceResult());
		// then
		verify(offerJpaRepository).save(any());
	}

	private CalculatePriceResult getCalculatePriceResult() {
		return new CalculatePriceResult.Builder().build();
	}

	private CalculatePriceCommand getCalculatePriceCommand() {
		return new CalculatePriceCommand.Builder().withAnswers(Collections.emptyList()).build();
	}

	private Offer getOffer() {
		return new Offer.Builder().build();
	}

	private Offer getExpiredOffer() {
		return new Offer.Builder().withCreationDate(EXPIRED_DATE).build();
	}
	
	private Offer getNotExpiredOffer() {
		return new Offer.Builder().withCreationDate(NOT_EXPIRED_DATE).build();
	}

}
