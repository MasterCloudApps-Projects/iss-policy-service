package es.urjc.code.policy.infrastructure.adapter.converter;

import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import es.urjc.code.policy.domain.Cover;
import es.urjc.code.policy.domain.Person;
import es.urjc.code.policy.domain.PolicyVersion;
import es.urjc.code.policy.domain.vo.DateRange;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.CoverEntity;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.PolicyVersionEntity;

@Component
public class PolicyVersionEntityToPolicyVersionConverter implements Converter<PolicyVersionEntity, PolicyVersion> {
	
	@Override
	public PolicyVersion convert(PolicyVersionEntity source) {
		final DateRange dateRange = new DateRange.Builder()
				                                 .withFrom(source.getCoverPeriod().getFrom()) 
				                                 .withTo(source.getCoverPeriod().getTo()) 
				                                 .build();
        final Person person = new Person.Builder()
        		                        .withFirstName(source.getPolicyHolder().getFirstName())
        		                        .withLastName(source.getPolicyHolder().getLastName())
        		                        .withPesel(source.getPolicyHolder().getPesel())
        		                        .build();
        
        final DateRange versionValidity  = new DateRange.Builder()
                                                        .withFrom(source.getVersionValidityPeriod().getFrom()) 
                                                        .withTo(source.getVersionValidityPeriod().getTo()) 
                                                        .build();  

        
		return new PolicyVersion.Builder()
				                      .withAccountNumber(source.getAccountNumber())
				                      .withCoverPeriod(dateRange)
				                      .withId(source.getId())
				                      .withPolicyHolder(person)
				                      .withProductCode(source.getProductCode())
				                      .withTotalPremiumAmount(source.getTotalPremiumAmount())
				                      .withVersionNumber(source.getVersionNumber())
				                      .withVersionValidityPeriod(versionValidity)
				                      .withCovers(source.getCovers().stream().map(this::toCover).collect(Collectors.toSet()))
				                      .build();
	}
	
	private Cover toCover(CoverEntity cover) {
		return new Cover.Builder().withId(cover.getId()).withCode(cover.getCode()).withPrice(cover.getPrice()).build();
	}

}
