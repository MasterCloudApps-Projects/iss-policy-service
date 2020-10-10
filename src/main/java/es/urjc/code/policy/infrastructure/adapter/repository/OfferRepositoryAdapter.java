package es.urjc.code.policy.infrastructure.adapter.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.urjc.code.policy.application.port.outgoing.LoadOfferPort;
import es.urjc.code.policy.application.port.outgoing.UpdateOfferPort;
import es.urjc.code.policy.domain.Offer;
import es.urjc.code.policy.domain.OfferStatus;
import es.urjc.code.policy.exception.BusinessException;
import es.urjc.code.policy.infrastructure.adapter.converter.OfferEntityToOfferConverter;
import es.urjc.code.policy.infrastructure.adapter.converter.OfferToOfferEntityConverter;
import es.urjc.code.policy.infrastructure.adapter.repository.entity.OfferEntity;
import es.urjc.code.policy.infrastructure.adapter.repository.jpa.OfferJpaRepository;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceCommand;
import es.urjc.code.policy.service.api.v1.commands.calculateprice.CalculatePriceResult;
import es.urjc.code.policy.service.api.v1.commands.createoffer.dto.QuestionAnswer;

@Service
@Transactional
public class OfferRepositoryAdapter implements LoadOfferPort, UpdateOfferPort {
	
	private final OfferJpaRepository offerJpaRepository;
	private final OfferEntityToOfferConverter offerEntityToOfferConverter;
	private final OfferToOfferEntityConverter offerToOfferEntityConverter;
	
	@Autowired
	public OfferRepositoryAdapter(OfferJpaRepository offerJpaRepository,OfferEntityToOfferConverter offerEntityToOfferConverter,OfferToOfferEntityConverter offerToOfferEntityConverter) {
		this.offerJpaRepository = offerJpaRepository;
		this.offerEntityToOfferConverter = offerEntityToOfferConverter;
		this.offerToOfferEntityConverter = offerToOfferEntityConverter;
	}

	@Override
	public Offer getOffer(String offerNumber) {
		final OfferEntity offerEntity = offerJpaRepository.getByNumber(offerNumber);
		final Offer offer = offerEntityToOfferConverter.convert(offerEntity);
		if (offer.isExpired(LocalDate.now())) {
			throw new BusinessException("Offer has expired"); 
		}
		return offer;
	}

	@Override
	public Offer createOffer(CalculatePriceCommand calcPriceCmd, CalculatePriceResult calcPriceResult) {
		final Offer offer = newOffer(calcPriceCmd, calcPriceResult);
		final OfferEntity offerEntity = offerToOfferEntityConverter.convert(offer);
		offerJpaRepository.save(offerEntity);
		return offer;
	}

	private Offer newOffer(CalculatePriceCommand calcPriceCmd, CalculatePriceResult calcPriceResult) {
		final Offer offer = new Offer.Builder()
		         .withNumber(UUID.randomUUID().toString())
		         .withProductCode(calcPriceCmd.getProductCode())
		         .withPolicyFrom(calcPriceCmd.getPolicyFrom())
		         .withPolicyTo(calcPriceCmd.getPolicyTo())
		         .withAnswers(constructAnswers(calcPriceCmd.getAnswers()))
		         .withTotalPrice(calcPriceResult.getTotalPrice())
		         .withCoversPrices(calcPriceResult.getCoversPrices())
		         .withStatus(OfferStatus.NEW)
		         .withCreationDate(LocalDate.now())
		         .build();
		return offer;
	}

    private static Map<String, String> constructAnswers(List<QuestionAnswer> answers) {
        return answers.stream()
                .collect(Collectors.toMap(QuestionAnswer::getQuestionCode,
                        a -> a.getAnswer() != null ? a.getAnswer().toString() : null)
                );
    }
}
