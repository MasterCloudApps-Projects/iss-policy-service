package es.urjc.code.policy.infrastructure.adapter.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.urjc.code.policy.domain.Person;
import es.urjc.code.policy.domain.PolicyVersion;
import es.urjc.code.policy.domain.vo.DateRange;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.PolicyVersionEntity;

public class PolicyVersionToPolicyVersionEntityConverterTest {
	
	private static final String PESEL = "111111116";
	private static final String LASTNAME = "Poirier";
	private static final String FIRSTNAME = "François";
	private static final String ACCOUNT_NUMBER = "2738123834783247723";
	private static final String PRODUCT_CODE = "Pakiet Gold";
	
	private PolicyVersionToPolicyVersionEntityConverter sut;
	
	@BeforeEach
	public void setUp() {
		this.sut = new PolicyVersionToPolicyVersionEntityConverter();
	}
	
	@Test
	public void shouldBeConvert() {
		final PolicyVersion version =  getPolicyVersion();
		final PolicyVersionEntity response = this.sut.convert(version);
		assertEquals(PRODUCT_CODE, response.getProductCode());
		assertEquals(ACCOUNT_NUMBER,response.getAccountNumber());
	}
	
	private PolicyVersion getPolicyVersion() {
		return new PolicyVersion.Builder()
        .withId(UUID.randomUUID())
        .withVersionNumber(1L)
        .withProductCode(PRODUCT_CODE)
        .withPolicyHolder(new Person.Builder().withFirstName(FIRSTNAME).withLastName(LASTNAME).withPesel(PESEL).build())
        .withAccountNumber(ACCOUNT_NUMBER)
        .withCoverPeriod(DateRange.between(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31)))
        .withVersionValidityPeriod(DateRange.between(LocalDate.of(2018, 1, 1), LocalDate.of(9999, 12, 31)))
        .withTotalPremiumAmount(new BigDecimal("199")).build();
	}
}
