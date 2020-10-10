package es.urjc.code.policy.infrastructure.adapter.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.domain.OfferStatus;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.OfferEntity;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.OfferStatusEnum;

@Component
public class OfferToOfferEntityConverter implements Converter<Offer, OfferEntity> {

	@Override
	public OfferEntity convert(Offer source) {
		return new OfferEntity.Builder().withAnswers(source.getAnswers()).withCoversPrices(source.getCoversPrices())
				.withCreationDate(source.getCreationDate()).withId(source.getId()).withNumber(source.getNumber())
				.withPolicyFrom(source.getPolicyFrom()).withPolicyTo(source.getPolicyTo())
				.withProductCode(source.getProductCode())
				.withStatus(source.getStatus() == OfferStatus.NEW ? OfferStatusEnum.NEW
						: OfferStatusEnum.CONVERTED_TO_POLICY)
				.withTotalPrice(source.getTotalPrice()).build();
	}

}
