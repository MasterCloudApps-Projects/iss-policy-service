package es.urjc.code.policy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import es.urjc.code.policy.domain.AgentRef;
import es.urjc.code.policy.domain.Person;
import es.urjc.code.policy.domain.Policy;
import es.urjc.code.policy.domain.PolicyVersion;
import es.urjc.code.policy.domain.vo.DateRange;

public class PolicyBuilder {
	
	public static final String POLICY_NUMBER = "P1212121";

	public static Policy build() {
			Set<PolicyVersion> versions = new HashSet<>(Arrays.asList(
	                new PolicyVersion.Builder()
	                        .withId(UUID.randomUUID())
	                        .withVersionNumber(1L)
	                        .withProductCode("Pakiet Gold")
	                        .withPolicyHolder(new Person.Builder().withFirstName("François").withLastName("Poirier").withPesel("111111116").build())
	                        .withAccountNumber("2738123834783247723")
	                        .withCoverPeriod(DateRange.between(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31)))
	                        .withVersionValidityPeriod(DateRange.between(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 31)))
	                        .withTotalPremiumAmount(new BigDecimal("199")).build(),
	                new PolicyVersion.Builder()
	                        .withId(UUID.randomUUID())
	                        .withVersionNumber(2L)
	                        .withProductCode("Pakiet Gold")
	                        .withPolicyHolder(new Person.Builder().withFirstName("François").withLastName("Poirier").withPesel("111111116").build())
	                        .withAccountNumber("2738123834783247723")
	                        .withCoverPeriod(DateRange.between(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31)))
	                        .withVersionValidityPeriod(DateRange.between(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 31)))
	                        .withTotalPremiumAmount(new BigDecimal("199")).build()
	                )
	        );
	        
			return new Policy.Builder()
					         .withNumber(POLICY_NUMBER)
					         .withAgent(new AgentRef.Builder().withLogin("admin").build())
					         .withVersions(versions)
					         .build();
	 }

}
