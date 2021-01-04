package es.urjc.code.policy.infrastructure.adapter.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.urjc.code.policy.domain.AgentRef;
import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.domain.OfferStatus;
import es.urjc.code.policy.domain.Person;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.domain.PolicyVersion;
import es.urjc.code.policy.domain.vo.DateRange;
import es.urjc.code.policy.exception.EntityNotFoundException;
import es.urjc.code.policy.infrastructure.adapter.repository.jpa.PolicyJpaRepository;

class PolicyRepositoryAdapterTest {

	public static final String POLICY_NUMBER = "P1212121";
	private static final String CODE_CAR = "CAR";
	private static final String OFFER_NUMBER = "111";
	
	private PolicyJpaRepository policyJpaRepository;
	private PolicyRepositoryAdapter sut;
	
	@BeforeEach
	public void setUp() {
		this.policyJpaRepository = Mockito.mock(PolicyJpaRepository.class);
		this.sut = new PolicyRepositoryAdapter(policyJpaRepository);
	}
	
	
	@Test
	void shouldNotBeUpdateTerminateState() {
		// given
		when(policyJpaRepository.findByNumber(POLICY_NUMBER)).thenReturn(Optional.empty());
		// when
		assertThrows(EntityNotFoundException.class, () -> {
			this.sut.updateTerminateState(POLICY_NUMBER);
		});
		// then
		verify(policyJpaRepository).findByNumber(POLICY_NUMBER);
	}
	
	@Test
	void shouldBeUpdateTerminateState() {
		// given
		Policy policy = getPolicy();
		when(policyJpaRepository.findByNumber(POLICY_NUMBER)).thenReturn(Optional.of(policy)); 
		when(policyJpaRepository.save(policy)).thenReturn(policy);
		// when
		Policy response = this.sut.updateTerminateState(POLICY_NUMBER);
		// then
		verify(policyJpaRepository).findByNumber(POLICY_NUMBER);
		verify(policyJpaRepository).save(any());
		assertEquals(POLICY_NUMBER, response.getNumber());
	}
	

	@Test
	void shouldBeCreatePolicy() {
		// given
		final Offer offer = getOffer();
		final Person policyHolder = getPerson();
		final AgentRef agent = getAgentRef();
		Policy policy = getPolicy(); 
		when(policyJpaRepository.save(policy)).thenReturn(policy);
		// when
		this.sut.createPolicy(offer, policyHolder, agent);
		// then
		verify(policyJpaRepository).save(any());
	}
	
	@Test
	void shouldNotBeGetPolicy() {
		// given
		when(policyJpaRepository.findByNumber(POLICY_NUMBER)).thenReturn(Optional.empty());
		// when
		assertThrows(EntityNotFoundException.class, () -> {
			this.sut.getPolicy(POLICY_NUMBER);
		});
		// then
		verify(policyJpaRepository).findByNumber(POLICY_NUMBER);
	}
	
	@Test
	void shouldBeGetPolicy() {
		// given
		when(policyJpaRepository.findByNumber(POLICY_NUMBER)).thenReturn(Optional.of(getPolicy()));
		// when
		final Policy response = this.sut.getPolicy(POLICY_NUMBER);
		// then
		verify(policyJpaRepository).findByNumber(POLICY_NUMBER);
		assertNotNull(response);
	}


	private Policy getPolicy() {
		Set<PolicyVersion> versions = new HashSet<>(Arrays.asList(
                new PolicyVersion.Builder()
                        .withId(UUID.randomUUID())
                        .withVersionNumber(1L)
                        .withProductCode("Pakiet Gold")
                        .withPolicyHolder(new Person.Builder().withFirstName("François").withLastName("Poirier").withPesel("111111116").build())
                        .withAccountNumber("2738123834783247723")
                        .withCoverPeriod(DateRange.between(LocalDate.now().minusMonths(6), LocalDate.now().plusMonths(6)))
                        .withVersionValidityPeriod(DateRange.between(LocalDate.now().minusMonths(6), LocalDate.now().plusMonths(6)))
                        .withTotalPremiumAmount(new BigDecimal("199")).build(),
                new PolicyVersion.Builder()
                        .withId(UUID.randomUUID())
                        .withVersionNumber(2L)
                        .withProductCode("Pakiet Gold")
                        .withPolicyHolder(new Person.Builder().withFirstName("François").withLastName("Poirier").withPesel("111111116").build())
                        .withAccountNumber("2738123834783247723")
                        .withCoverPeriod(DateRange.between(LocalDate.now().minusMonths(6), LocalDate.now().plusMonths(6)))
                        .withVersionValidityPeriod(DateRange.between(LocalDate.now().minusMonths(6), LocalDate.now().plusMonths(6)))
                        .withTotalPremiumAmount(new BigDecimal("199")).build()
                )
        );
        
		return new Policy.Builder()
				         .withNumber(POLICY_NUMBER)
				         .withAgent(new AgentRef.Builder().withLogin("admin").build())
				         .withVersions(versions)
				         .build();
	}

	private AgentRef getAgentRef() {
		return new AgentRef.Builder().withLogin("admin").build();
	}
	
	private Person getPerson() {
		return new Person.Builder().withFirstName("François").withLastName("Poirier").withPesel("111111116").build();
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
