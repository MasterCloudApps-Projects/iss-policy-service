package es.urjc.code.policy.infrastructure.adapter.repository.jpa;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import es.urjc.code.policy.base.AbstractContainerBaseTest;
import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.domain.OfferStatus;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(initializers = { AbstractContainerBaseTest.PropertiesInitializer.class })
class OfferJpaRepositoryIT extends AbstractContainerBaseTest {

	private static final String OFFER_NUMBER = "111";
	private static final String CODE_CAR = "CAR";
	
	@Autowired
	private OfferJpaRepository offerJpaRepository;
	
	@BeforeEach
	public void setUp() {
		Offer entity = new Offer.Builder()
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
		offerJpaRepository.save(entity);
	}
	
	@Test
	void testWhenGetByNumberThenReturnOffer() {
		final var o = offerJpaRepository.getByNumber(OFFER_NUMBER);
		assertEquals(CODE_CAR,o.getProductCode());
	}
}
