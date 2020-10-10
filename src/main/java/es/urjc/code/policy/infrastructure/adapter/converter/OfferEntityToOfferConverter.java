package es.urjc.code.policy.infrastructure.adapter.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.domain.OfferStatus;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.OfferEntity;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.OfferStatusEnum;

@Component
public class OfferEntityToOfferConverter implements Converter<OfferEntity, Offer> {

	@Override
	public Offer convert(OfferEntity source) {
		return new Offer.Builder().withAnswers(source.getAnswers()).withCoversPrices(source.getCoversPrices())
				.withCreationDate(source.getCreationDate()).withId(source.getId()).withNumber(source.getNumber())
				.withPolicyFrom(source.getPolicyFrom()).withPolicyTo(source.getPolicyTo())
				.withProductCode(source.getProductCode())
				.withStatus(
						source.getStatus() == OfferStatusEnum.NEW ? OfferStatus.NEW : OfferStatus.CONVERTED_TO_POLICY)
				.withTotalPrice(source.getTotalPrice()).build();
	}

}
