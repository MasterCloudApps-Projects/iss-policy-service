package es.urjc.code.policy.infrastructure.adapter.converter;

import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import es.urjc.code.policy.domain.Cover;
import es.urjc.code.policy.domain.PolicyVersion;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.CoverEntity;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.DateRangeEmbeddable;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.PersonEmbeddable;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.PolicyVersionEntity;

@Component
public class PolicyVersionToPolicyVersionEntityConverter implements Converter<PolicyVersion, PolicyVersionEntity> {
	
	@Override
	public PolicyVersionEntity convert(PolicyVersion source) {
		final DateRangeEmbeddable dateRangeEmbeddable = new DateRangeEmbeddable.Builder()
				                                                         .withFrom(source.getCoverPeriod().getFrom()) 
				                                                         .withTo(source.getCoverPeriod().getTo()) 
				                                                         .build();
        final PersonEmbeddable personEmbeddable = new PersonEmbeddable.Builder()
        		                                                      .withFirstName(source.getPolicyHolder().getFirstName())
        		                                                      .withLastName(source.getPolicyHolder().getLastName())
        		                                                      .withPesel(source.getPolicyHolder().getPesel())
        		                                                      .build();
        
        final DateRangeEmbeddable versionValidityPeriod  = new DateRangeEmbeddable.Builder()
                                                                                  .withFrom(source.getVersionValidityPeriod().getFrom()) 
                                                                                  .withTo(source.getVersionValidityPeriod().getTo()) 
                                                                                  .build();  

        
		return new PolicyVersionEntity.Builder()
				                      .withAccountNumber(source.getAccountNumber())
				                      .withCoverPeriod(dateRangeEmbeddable)
				                      .withId(source.getId())
				                      .withPolicyHolder(personEmbeddable)
				                      .withProductCode(source.getProductCode())
				                      .withTotalPremiumAmount(source.getTotalPremiumAmount())
				                      .withVersionNumber(source.getVersionNumber())
				                      .withVersionValidityPeriod(versionValidityPeriod)
				                      .withCovers(source.getCovers().stream().map(this::toCoverEntity).collect(Collectors.toSet()))
				                      .build();
	}
	
	private CoverEntity toCoverEntity(Cover cover) {
		return new CoverEntity.Builder().withId(cover.getId()).withCode(cover.getCode()).withPrice(cover.getPrice()).build();
	}

}
